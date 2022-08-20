package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;

public interface RecommendService {

    boolean clickRecommend(Member member, Long articleId);

}
