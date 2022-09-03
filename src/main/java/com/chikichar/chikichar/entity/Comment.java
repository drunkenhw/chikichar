package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * article = 게시글 번호
 * member = 댓글 글쓴이
 * content = 댓글 내용
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private Comment(Article article, Member member, String content) {
        this.article = article;
        this.member = member;
        this.content = content;
    }

    public static Comment of(Article article, Member member, String content){
        Comment comment = new Comment(article, member, content);
        comment.registerInArticle(article);
        comment.setCommenter(member);
        System.out.println("of"+member);
        System.out.println("of"+member.getCommentList());
        return comment;
    }




    private void setCommenter(Member member) {
        if(this.member.getCommentList() != null){
            this.member.getCommentList().remove(this);
        }
        this.member = member;
        member.getCommentList().add(this);
        System.out.println(member);
        System.out.println(member.getCommentList());
    }
    private void registerInArticle(Article article) {
        if(this.article.getCommentList() != null){
            this.article.getCommentList().remove(this);
        }
        this.article = article;
        article.getCommentList().add(this);
    }

}
