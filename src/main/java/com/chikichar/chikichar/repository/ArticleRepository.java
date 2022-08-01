package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
