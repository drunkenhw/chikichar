package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.ArticleRequestDto;
import com.chikichar.chikichar.dto.Board.BoardSearchType;
import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.dto.page.CustomPageRequest;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.ArticleImage;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static com.chikichar.chikichar.EntityBuilder.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ArticleServiceImplTest {
    @Autowired
    ArticleService articleService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleImageRepository articleImageRepository;


    @BeforeEach
    void before(){
        insertDummy();

    }
    @Test
    @DisplayName("BoardDTO를 검색조건과 함께 페이지네이션으로 조회한다.")
    void getList() {

        //given
        BoardSearchType boardSearchType = new BoardSearchType();
        boardSearchType.setContent("content");
        boardSearchType.setNickname("query");
        boardSearchType.setTitle("title");
        boardSearchType.setBoardName("benz");

        CustomPageRequest customPageRequest = new CustomPageRequest();
        PageRequest pageRequest = customPageRequest.of();

        //when
        Page<NormalBoardArticleDto> dto = articleService.printArticleList(boardSearchType, pageRequest);

        //then
        assertThat(dto.getContent().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("해당 게시판에 글 등록을 한다.")
    void writeArticle(){
        //given
        Member member = memberRepository.findById(1L).orElseThrow();
        ArticleRequestDto articleRequestDto = ArticleRequestDto.builder()
                .title("title")
                .content("content")
                .boardName("benz")
                .build();
        //when
        Long articleId = articleService.writeArticle(member, articleRequestDto);
        //then
        Optional<Article> findArticle = articleRepository.findById(articleId);
        assertThat(findArticle.get().getId()).isEqualTo(articleId);

    }

    private void insertDummy() {
        Member member = createMember("querydsl", "query");
        memberRepository.save(member);
        Board board = createBoard();
        boardRepository.save(board);
        Article article = createArticle(board, member);
        articleRepository.save(article);
        Article article1 = createArticle(board, member);
        articleRepository.save(article1);
        ArticleImage articleImage = ArticleImage.of("a", "b", "c", article);
        articleImageRepository.save(articleImage);
    }

}