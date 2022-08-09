package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.member.domain.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @ToString.Exclude
    private Article article;

    public void setMember(Member member) {
        this.member = member;
    }

    private Recommend(Member member, Article article) {
        this.member = member;
        this.article = article;
    }

    public static Recommend of(Member member,Article article){
        article.getMember().pointUp();
        return new Recommend(member,article);
    }


}
