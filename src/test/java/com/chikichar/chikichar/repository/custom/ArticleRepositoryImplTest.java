package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.Board.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.*;
import com.chikichar.chikichar.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static com.chikichar.chikichar.EntityBuilder.*;

@Transactional
@SpringBootTest
class ArticleRepositoryImplTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void before() {
        insertDummyData();
    }

    @Test
    @DisplayName("Board dto에 맞는 쿼리가 발생한다.")
    void querydslTest() {
        List<Board> all = boardRepository.findAll();
        List<NormalBoardArticleDto> findBoardList = articleRepository.findByBoardId(all.get(0).getId());

        Assertions.assertThat(findBoardList.size()).isEqualTo(3);
    }

    private void insertDummyData(){
        Member member = createMember("email@naver.com", "querytest");
        Board board = createBoard();
        Article article = createArticle(board, member);
        Article article1 = createArticle(board, member);
        Article article2 = createArticle(board, member);
        Comment comment1 = createComment(article, member);
        Comment comment2 = createComment(article, member);
        Comment comment3 = createComment(article, member);
        ArticleImage articleImage = ArticleImage.of("h", "h", "h", article);
        ArticleImage articleImage1 = ArticleImage.of("h", "h", "h", article);
        ArticleImage articleImage2 = ArticleImage.of("h", "h", "h", article);
        ArticleImage articleImage3 = ArticleImage.of("h", "h", "h", article);
        articleImage.addInArticle(article);
        articleImage1.addInArticle(article);
        articleImage2.addInArticle(article);
        articleImage3.addInArticle(article);
        memberRepository.save(member);
        boardRepository.save(board);
        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleImageRepository.save(articleImage);
        articleImageRepository.save(articleImage1);
        articleImageRepository.save(articleImage2);
        articleImageRepository.save(articleImage3);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
    }
}