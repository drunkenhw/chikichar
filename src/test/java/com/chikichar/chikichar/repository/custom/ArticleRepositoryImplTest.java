package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.entity.*;
import com.chikichar.chikichar.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.chikichar.chikichar.EntityBuilder.*;

@Transactional
@SpringBootTest
class ArticleRepositoryImplTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
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