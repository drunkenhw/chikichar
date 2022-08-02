package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface RecommendService {

    boolean addRecommend(Long memberId, Long articleId);

}
