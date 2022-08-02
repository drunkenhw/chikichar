package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Optional<Recommend> findByMemberAndArticle(Member member, Article article);
}
