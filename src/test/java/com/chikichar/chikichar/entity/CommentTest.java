package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.repository.ArticleRepository;
import com.chikichar.chikichar.repository.BoardRepository;
import com.chikichar.chikichar.repository.CommentRepository;
import com.chikichar.chikichar.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CommentTest {

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
        Member member = EntityBuilder.createMember("before","before");
        memberRepository.save(member);
        Board board = EntityBuilder.createBoard();
        boardRepository.save(board);
        Article article = EntityBuilder.createArticle(board, member);
        articleRepository.save(article);
        Comment comment = EntityBuilder.createComment(article,member);
        commentRepository.save(comment);

        assertThat(member.getComments().size()).isEqualTo(1);
        System.out.println("member.getComments() = " + member.getComments());
        System.out.println("comment.getMember().getComments() = " + comment.getMember().getComments());

    }
}