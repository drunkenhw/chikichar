package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.repository.custom.ArticleRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryQuerydsl {


    List<Article> findAllByBoardId(Long boardId);
}
