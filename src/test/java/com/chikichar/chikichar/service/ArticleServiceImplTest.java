package com.chikichar.chikichar.service;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.dto.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.ArticleImage;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.chikichar.chikichar.EntityBuilder.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void getList(){
        //given
        Member member = createMember("querydsl", "query");
        Board board = createBoard();
        Article article = createArticle(board, member);
        ArticleImage articleImage = new ArticleImage("a", "b", "c",article);
        memberRepository.save(member);
        boardRepository.save(board);
        articleRepository.save(article);
        articleImageRepository.save(articleImage);

        //when
        List<NormalBoardArticleDto> boardDto = articleService.printArticleList(board.getId());
        //then
        assertThat(boardDto.size()).isEqualTo(1);
        assertThat(boardDto.get(0).getNickname()).isEqualTo(member.getNickname());
        assertThat(boardDto.get(0).getTitle()).isEqualTo(article.getTitle());
        assertThat(boardDto.get(0).getArticleImage().getId()).isEqualTo(articleImage.getId());

    }

}