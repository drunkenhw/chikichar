package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.model.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findAllByBoardId(Long boardId);
}
