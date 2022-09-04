package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * itemName = 상품 명
 * price = 가격
 * madeAt = 제조사
 * address = 주소
 * article = 게시글 번호
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int price;

    private String madeAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
    @Builder
    public Item(String itemName, int price, String madeAt, Article article) {
        this.itemName = itemName;
        this.price = price;
        this.madeAt = madeAt;
        this.article = article;
    }
}
