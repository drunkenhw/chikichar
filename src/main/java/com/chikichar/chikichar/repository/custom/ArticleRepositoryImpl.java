package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.QArticleImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.chikichar.chikichar.entity.QArticle.*;
import static com.chikichar.chikichar.entity.QArticleImage.*;
import static com.chikichar.chikichar.entity.QComment.*;

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

}
