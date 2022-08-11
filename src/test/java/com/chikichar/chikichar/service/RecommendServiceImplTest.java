package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

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

    @BeforeEach
    void beforeEach(){
         Member writer = Member.builder().email("AAA").build();
        Member saveWriter = memberRepository.save(writer);
        Member reader = Member.builder().email("BBB").build();
        memberRepository.save(reader);
        Article article = Article.builder().member(saveWriter).title("AA").content("aa").build();
        Article saveArticle = articleRepository.save(article);
    }

    @Test
    @DisplayName("추천 중복 방지 테스트")
    @Transactional
    @Commit
    public void recommendTest(){

        Member saveWriter = memberRepository.findById(1L).orElseThrow();
        Member saveReader = memberRepository.findById(2L).orElseThrow();
        Article saveArticle = articleRepository.findById(1L).orElseThrow();


        boolean firstRecommend = recommendService.clickRecommend(saveReader.getId(), saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(1);// 회원 포인트 +1

        boolean secondRecommend = recommendService.clickRecommend(saveReader.getId(), saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(0);// 회원 포인트 -1

        boolean thirdRecommend = recommendService.clickRecommend(saveReader.getId(), saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(1);// 회원 포인트 +1

        assertThat(firstRecommend).isEqualTo(true);  //처음 추천 시 true 반환
        assertThat(secondRecommend).isEqualTo(false); //두번째 추천 시 false 반환
        assertThat(thirdRecommend).isEqualTo(true); //세번째 추천 시 true 반환


    }
}