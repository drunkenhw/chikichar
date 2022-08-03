package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.BoardType;
import com.chikichar.chikichar.member.domain.Member;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.member.repository.MemberRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RecommendServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private RecommendService recommendService;
    
    @Test
    @DisplayName("추천 중복 방지 테스트")
    public void recommendTest(){
        Member writer = Member.builder().email("AAA").build();
        Member saveWriter = memberRepository.save(writer);
        Member reader = Member.builder().email("BBB").build();
        Member saveReader = memberRepository.save(reader);
        Article article = Article.builder().member(saveWriter).title("AA").boardType(BoardType.FOOD).content("aa").build();
        Article saveArticle = articleRepository.save(article);
        boolean firstRecommend = recommendService.addRecommend(saveReader.getId(), saveArticle.getId());
        boolean secondRecommend = recommendService.addRecommend(saveReader.getId(), saveArticle.getId());
        boolean thirdRecommend = recommendService.addRecommend(saveReader.getId(), saveArticle.getId());

        assertThat(firstRecommend).isEqualTo(true);  //처음 추천 시 true 반환 및 회원 포인트 +1
        assertThat(secondRecommend).isEqualTo(false); //두번째 추천 시 false 반환 및 회원 포인트 -1
        assertThat(thirdRecommend).isEqualTo(true); //세번째 추천 시 true 반환 및 회원 포인트 +1

    }
}