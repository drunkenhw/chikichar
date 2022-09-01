package com.chikichar.chikichar.repository.custom;

import com.chikichar.chikichar.dto.NormalBoardArticleDto;
import com.chikichar.chikichar.entity.*;
import com.chikichar.chikichar.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;

import static com.chikichar.chikichar.EntityBuilder.*;

@Transactional
@SpringBootTest
class ArticleQuerydslRepositoryTest {
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
    @Autowired
    ArticleQuerydslRepository articleQuerydslRepository;

    @BeforeEach
    void before(){
        Member member = createMember("email@naver.com", "querytest");
        Board board = createBoard();
        Article article = createArticle(board, member);
        Article article1 = createArticle(board, member);
        Article article2 = createArticle(board, member);
        Comment comment1 = createComment(article, member);
        Comment comment2 = createComment(article, member);
        Comment comment3 = createComment(article, member);
        ArticleImage articleImage = new ArticleImage("h","h","h",article);
        ArticleImage articleImage1 = new ArticleImage("h","h","h",article);
        ArticleImage articleImage2 = new ArticleImage("h","h","h",article);
        ArticleImage articleImage3 = new ArticleImage("h","h","h",article);
        articleImageRepository.save(articleImage);
        articleImageRepository.save(articleImage1);
        articleImageRepository.save(articleImage2);
        articleImageRepository.save(articleImage3);
        memberRepository.save(member);
        boardRepository.save(board);
        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
    }

    @Test
    @Commit
    void querydslTest(){
        List<Board> all = boardRepository.findAll();
        List<NormalBoardArticleDto> findBoardList = articleQuerydslRepository.findByBoardId(all.get(0).getId());
        for (NormalBoardArticleDto normalBoardArticleDto : findBoardList) {
            System.out.println("normalBoardArticleDto = " + normalBoardArticleDto);
        }
        List<NormalBoardArticleDto> findBoardDto = articleQuerydslRepository.findByBoardId(all.get(0).getId());
//        List<ArticleImage> findArticleImage = articleImageRepository.findAllByArticleId(findBoardDto.);
//        findBoardDto.forEach(i -> i.putImageList(findArticleImage));
        System.out.println(findBoardDto.get(0));
    }
}