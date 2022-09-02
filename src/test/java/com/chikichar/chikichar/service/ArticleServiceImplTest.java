package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.ArticleImage;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleImageRepository;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    @DisplayName("BoardDTO를 조회한다.")
    void getList(){
        //given
        Member member = createMember("querydsl", "query");
        memberRepository.save(member);
        Board board = createBoard();
        boardRepository.save(board);
        Article article = createArticle(board, member);
        articleRepository.save(article);
        ArticleImage articleImage =ArticleImage.of("a", "b", "c",article);
        articleImageRepository.save(articleImage);

        //when
        List<NormalBoardArticleDto> boardDto = articleService.printArticleList(board.getId());
        //then
        assertThat(boardDto.size()).isEqualTo(1);
        assertThat(boardDto.get(0).getNickname()).isEqualTo(member.getNickname());
        assertThat(boardDto.get(0).getTitle()).isEqualTo(article.getTitle());
        assertThat(boardDto.get(0).getImagePath()).isEqualTo(articleImage.getPath());

    }

}