package com.chikichar.chikichar.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_image_id")
    private Long id;

    @Column(name = "article_image_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
