package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.dto.Board.QNormalBoardArticleDto;
import com.chikichar.chikichar.entity.QArticleImage;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.chikichar.chikichar.entity.QArticle.*;
import static com.chikichar.chikichar.entity.QArticleImage.*;
import static com.chikichar.chikichar.entity.QComment.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;


    public List<NormalBoardArticleDto> findByBoardId(Long boardId) {
        QArticleImage articleImage1 = new QArticleImage("a");
        return queryFactory.select(
                        Projections.bean(NormalBoardArticleDto.class,
                                article.member.nickname,
                                article.title,
                                article.content,
                                article.viewCount,
                                comment.count().as("commentCount"),
                                article.regDate,
                                articleImage.path.as("imagePath")
                        ))
                .from(article)
                .leftJoin(comment)
                .on(comment.article.eq(article))
                .leftJoin(articleImage)
                .on(articleImage.article.eq(article),
                        articleImage.id.eq(
                                queryFactory
                                        .select(articleImage1.id.max())
                                        .from(articleImage1)
                                        .where(articleImage1.article.eq(article))
                        ))
                .where(article.board.id.eq(boardId))
                .groupBy(article)
                .fetch();

    }

    @Override
    public Page<NormalBoardArticleDto> searchBoardPaging(BoardSearchType searchType, String boardName, Pageable pageable) {

        QArticleImage articleImage1 = new QArticleImage("a");

        List<NormalBoardArticleDto> result = queryFactory
                .select(new QNormalBoardArticleDto(
                        article.member.nickname,
                        article.title,
                        article.viewCount,
                        comment.count(),
                        article.regDate,
                        articleImage.path))
                .from(article)
                .leftJoin(comment)
                .on(comment.article.eq(article))
                .leftJoin(articleImage)
                .on(articleImage.article.eq(article),
                        articleImage.id.eq(
                                queryFactory
                                        .select(articleImage1.id.max())
                                        .from(articleImage1)
                                        .where(articleImage1.article.eq(article))
                        ))
                .where(nicknameContains(searchType.getNickname()),
                        article.board.name.eq(boardName))
                .groupBy(article)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(article.count())
                .from(article)
                .where(nicknameContains(searchType.getNickname()),
                        titleContains(searchType.getTitle()),
                        contentContains(searchType.getContent()),
                        article.board.name.eq(boardName));
        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nicknameContains(String nickname) {
        return hasText(nickname) ? article.member.nickname.contains(nickname) : null;
    }
    private BooleanExpression titleContains(String title) {
        return hasText(title) ? article.title.contains(title) : null;
    }
    private BooleanExpression contentContains(String content) {
        return hasText(content) ? article.content.contains(content) : null;
    }

}
