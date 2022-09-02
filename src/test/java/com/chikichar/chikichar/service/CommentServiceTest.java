package com.chikichar.chikichar.service;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Comment;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.CommentRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("댓글을 달면 회원의 Comment 리스트에 추가한다.")
    void memberCommentListTest(){
        Member member = EntityBuilder.createMember("comment","comment");
        memberRepository.save(member);
        Board board = EntityBuilder.createBoard();
        boardRepository.save(board);
        Article article = EntityBuilder.createArticle(board, member);
        articleRepository.save(article);
        Comment comment = EntityBuilder.createComment(article,member);
        commentRepository.save(comment);
        Comment comment1 = EntityBuilder.createComment(article,member);
        commentRepository.save(comment1);

        assertThat(member.getComments().size()).isEqualTo(2);
        System.out.println("member.getComments() = " + member.getComments());
        System.out.println("comment.getMember().getComments() = " + comment.getMember().getComments());

    }
}