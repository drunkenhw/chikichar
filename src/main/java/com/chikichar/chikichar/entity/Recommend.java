package com.chikichar.chikichar.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@ToString(exclude = {"member", "article"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private void setRecommender(Member member) {
        if(this.member.getRecommendList() != null){
            this.member.getRecommendList().remove(this);
        }
        this.member = member;
        member.getRecommendList().add(this);
    }


    private Recommend(Member member, Article article) {
        this.member = member;
        this.article = article;
    }

    public static Recommend of(Member member,Article article){
        Recommend recommend = new Recommend(member, article);
        recommend.setRecommender(member);
        article.getMember().pointUp();
        return recommend;
    }



}
