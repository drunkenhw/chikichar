package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleImageRepository extends JpaRepository<ArticleImage,Long> {

    List<ArticleImage> findAllByArticleId(Long articleId);
}
