package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.QArticle;
import com.chikichar.chikichar.entity.QArticleImage;
import com.chikichar.chikichar.entity.QComment;
import com.chikichar.chikichar.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.chikichar.chikichar.entity.QArticle.*;
import static com.chikichar.chikichar.entity.QArticleImage.*;
import static com.chikichar.chikichar.entity.QComment.*;
import static com.chikichar.chikichar.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class ArticleQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<NormalBoardArticleDto> findByBoardId(Long boardId) {
        QArticleImage articleImage1 = new QArticleImage("a");
        //TODO 쿼리 수정요망
        return queryFactory.select(
                        Projections.bean(NormalBoardArticleDto.class,
                                article.member.nickname,
                                article.title,
                                article.content,
                                article.viewCount,
                                comment.count().as("commentCount"),
                                article.regDate,
                                articleImage
                        ))
                .from(article)
                .leftJoin(comment).on(comment.article.eq(article))
                .leftJoin(articleImage).on(articleImage.article.eq(article),
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
