package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.BaseEntity;
import com.chikichar.chikichar.member.domain.Member;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * member = 글쓴이
 * title = 글 제목
 * content = 글 내용
 * boardType = 게시글 타입. (일반, 맛집, 드라이브, 거래)
 * locationX = 위치 X축
 * locationY = 위치 Y축
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
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


    @Column(name = "location_x")
    private double locationX;

    @Column(name = "location_y")
    private double locationY;



    @Builder
    public Article(Member member, String title, String content,  double locationX, double locationY) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.locationX = locationX;
        this.locationY = locationY;
    }





}