package com.chikichar.chikichar.service;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
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
@Transactional
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
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void beforeEach(){
        Member writer = EntityBuilder.createMember();
        Member saveWriter = memberRepository.save(writer);

        Member reader = Member.builder().email("BBB").memberRole(MemberRole.USER).build();
        memberRepository.save(reader);

        Board board = EntityBuilder.createBoard();
        Board saveBoard = boardRepository.save(board);

        Article article = Article.builder().member(saveWriter).title("AA").content("aa").board(saveBoard).build();
        Article saveArticle = articleRepository.save(article);

        System.out.println("------------------beforeEach---------------------------");

    }

    @Test
    @DisplayName("추천 중복 방지 테스트")
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