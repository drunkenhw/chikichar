package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.BaseTimeEntity;
import lombok.*;

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
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String  content;

    private int viewCount;
    //TODO 추천수, 이미지를 칼럼에 넣는 것 고려해봐야함
    @Embedded
    private Address address;

    @Column(name = "location_x")
    private Double locationX;

    @Column(name = "location_y")
    private Double locationY;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleImage> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();


    @Builder
    public Article(Board board, Member member, String title, String content, int viewCount, Address address, Double locationX, Double locationY) {
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;

    }


}