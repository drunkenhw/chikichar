package com.chikichar.chikichar.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *  name = 이미지 원본 이름
 *  uuid = 이미지의 uuid값
 *  path = 이미지 경로
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_image_id")
    private Long id;

    @Column(name = "article_image_name")
    private String name;

    private String uuid;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    protected ArticleImage(String name, String uuid, String path) {
        this.name = name;
        this.uuid = uuid;
        this.path = path;
    }
    public static ArticleImage of(String name, String uuid, String path, Article article){
        ArticleImage articleImage = new ArticleImage(name, uuid, path);
        articleImage.addInArticle(article);
        return articleImage;
    }

    public void addInArticle(Article article) {
        if(this.article != null){
            this.article.getImageList().remove(this);
        }
        this.article = article;
        article.getImageList().add(this);
    }
}
