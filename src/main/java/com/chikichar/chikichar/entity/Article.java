package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.entity.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Column(nullable = false)
    private String title;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String  content;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private BoardType boardType;  //게시글 타입. (일반, 맛집, 드라이브, 거래)

    @Column(name = "map_x")
    private Long mapX;  // 지도 x축

    @Column(name = "map_y")
    private Long mapY;      //지도 y축

    @OneToOne(mappedBy = "article")
    private Product product;

    @OneToMany(mappedBy = "article")
    private List<ArticleImage> articleImages = new ArrayList<>();  //이미지 목록

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();  // 댓글 목록

    @OneToMany(mappedBy = "article")
    private  List<Recommend> recommends = new ArrayList<>();  //추천 수


}
