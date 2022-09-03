package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.entity.Recommend;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class RecommendServiceImpl implements RecommendService{
    private final ArticleRepository articleRepository;
    private final RecommendRepository recommendRepository;

    @Transactional
    @Override
    public boolean clickRecommend(Member member, Long articleId) {
        //TODO Exception 처리 해야함
        Article findArticle = articleRepository.findById(articleId).orElseThrow();

        if(isAlreadyRecommend(member, findArticle)){
            return false;
        }
        recommendRepository.save(Recommend.of(member,findArticle));
        return true;

    }

    private boolean isAlreadyRecommend(Member member, Article article) {
        Optional<Recommend> findRecommend = recommendRepository.findByMemberAndArticle(member, article);
        if(findRecommend.isPresent()){
            Recommend recommend = findRecommend.get();
            cancelRecommend(article, recommend);
        }
        return findRecommend.isPresent();
    }

    private void cancelRecommend(Article article, Recommend recommend) {
        recommendRepository.delete(recommend);
        recommend.getMember().getRecommendList().remove(recommend);
        article.getMember().pointDown();
    }


}