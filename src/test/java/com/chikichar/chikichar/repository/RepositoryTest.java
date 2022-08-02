package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class RepositoryTest {
    @Autowired
    private  ArticleRepository articleRepository;
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private  ItemRepository itemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleImageRepository articleImageRepository;
    @Autowired
    private RecommendRepository recommendRepository;

    @Test
    public void dummyTest(){
        Member member = Member.builder()
                .email("dummy@dummy.com")
                .name("han")
                .address(Address.builder()
                        .city("busan")
                        .street("street")
                        .zipcode("13213")
                        .build())
                .password("dsadasdsad")
                .memberRole(MemberRole.ADMIN)
                .build();
        Member savedMember = memberRepository.save(member);

        Article article = Article.builder()
                .member(savedMember)
                .title("제목")
                .content("내용")
                .boardType(BoardType.FOOD)
                .locationX(32.2323)
                .locationY(32.4321312)
                .build();

        Article saveArticle = articleRepository.save(article);

        Item item = Item.builder()
                .address(Address.builder().zipcode("12313").street("거리").city("서울").build())
                .article(saveArticle)
                .madeAt("DIY")
                .price(101000)
                .build();

        Item saveItem = itemRepository.save(item);

        Comment comment = new Comment(saveArticle,savedMember,"!@#!@#!@#!@");

        Comment saveComment = commentRepository.save(comment);

        ArticleImage articleImage = ArticleImage.builder()
                .article(saveArticle)
                .name("grim")
                .uuid("$#@$@DFSERWER#@$@")
                .path("//23//s")
                .build();

        ArticleImage saveImage = articleImageRepository.save(articleImage);

        Recommend recommend = new Recommend(savedMember,saveArticle);

        Recommend saveRecommend = recommendRepository.save(recommend);

        System.out.println("saveArticle = " + saveArticle.getRecommends());

    }



}