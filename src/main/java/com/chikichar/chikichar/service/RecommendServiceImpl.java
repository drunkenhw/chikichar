package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.entity.Recommend;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendServiceImpl implements RecommendService{
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final RecommendRepository recommendRepository;

    @Override
    public boolean addRecommend(Long memberId, Long articleId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Article article = articleRepository.findById(articleId).orElseThrow();

        if(isRecommend(member, article)){
            return false;
        }
        recommendRepository.save(new Recommend(member,article));
        return true;

    }

    private boolean isRecommend(Member member, Article article) {
        return recommendRepository.findByMemberAndArticle(member,article).isPresent();
    }
}
