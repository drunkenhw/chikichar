package com.chikichar.chikichar.service;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.entity.Recommend;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.repository.RecommendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Test
    @DisplayName("처음 추천할 때는 true를 반환하지만 두번째는 false를 반환한다.")
    public void recommendTest(){
        Member writer = EntityBuilder.createMember("writer", "writer");
        Member saveWriter = memberRepository.save(writer);

        Member reader = EntityBuilder.createMember("reader","reader");
        Member saveReader = memberRepository.save(reader);

        Board board = EntityBuilder.createBoard();
        Board saveBoard = boardRepository.save(board);

        Article article = Article.builder().member(saveWriter).title("AA").content("aa").board(saveBoard).build();
        Article saveArticle = articleRepository.save(article);

        Article article2 = Article.builder().member(saveWriter).title("AA").content("aa").board(saveBoard).build();
        Article saveArticle2 = articleRepository.save(article2);


        boolean firstRecommend = recommendService.clickRecommend(saveReader, saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(1);// 회원 포인트 +1

        boolean secondRecommend = recommendService.clickRecommend(saveReader, saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(0);// 회원 포인트 -1

        boolean thirdRecommend = recommendService.clickRecommend(saveReader, saveArticle.getId());
        assertThat(saveWriter.getPoint()).isEqualTo(1);// 회원 포인트 +1

        recommendService.clickRecommend(saveReader, saveArticle2.getId());

        assertThat(firstRecommend).isEqualTo(true);  //처음 추천 시 true 반환
        assertThat(secondRecommend).isEqualTo(false); //두번째 추천 시 false 반환
        assertThat(thirdRecommend).isEqualTo(true); //세번째 추천 시 true 반환
        List<Recommend> recommends = saveReader.getRecommends();
        System.out.println("recommends = " + recommends);

    }

    @Test
    @DisplayName("회원이 추천을 취소하면 회원의 추천 리스트에서 제거된다")
    void memberCancelRecommend(){
        Member writer = EntityBuilder.createMember("writer", "writer");
        Member saveWriter = memberRepository.save(writer);

        Member reader = EntityBuilder.createMember("reader","reader");
        Member saveReader = memberRepository.save(reader);

        Board board = EntityBuilder.createBoard();
        Board saveBoard = boardRepository.save(board);

        Article article = Article.builder().member(saveWriter).title("AA").content("aa").board(saveBoard).build();
        Article saveArticle = articleRepository.save(article);

        recommendService.clickRecommend(saveReader, saveArticle.getId());

        recommendService.clickRecommend(saveReader, saveArticle.getId());

        assertThat(saveReader.getRecommends().size()).isEqualTo(0);

    }

    @Test
    @DisplayName("회원이 추천을 하면 회원의 추천리스트에 추가된다.")
    void memberAddRecommend(){
        Member writer = EntityBuilder.createMember("writer", "writer");
        Member saveWriter = memberRepository.save(writer);

        Member reader = EntityBuilder.createMember("reader","reader");
        Member saveReader = memberRepository.save(reader);

        Board board = EntityBuilder.createBoard();
        Board saveBoard = boardRepository.save(board);

        Article article = Article.builder().member(saveWriter).title("AA").content("aa").board(saveBoard).build();
        Article saveArticle = articleRepository.save(article);

        recommendService.clickRecommend(saveReader, saveArticle.getId());


        assertThat(saveReader.getRecommends().size()).isEqualTo(1);

    }
}