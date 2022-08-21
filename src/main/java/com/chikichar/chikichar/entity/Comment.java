package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Comment(Article article, Member member, String content) {
        this.article = article;
        this.member = member;
        this.content = content;
        this.setCommenter(member);
    }

    private void setCommenter(Member member) {
        if(this.member.getComments() != null){
            this.member.getComments().remove(this);
        }
        this.member = member;
        member.getComments().add(this);
    }
}
