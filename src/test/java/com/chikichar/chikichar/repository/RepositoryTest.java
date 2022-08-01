package com.chikichar.chikichar.repository;

import com.chikichar.chikichar.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void test(){
        Member member = new Member("dummy@dummy.com", MemberRole.ADMIN,"han","jwt",new Address("busan","citizen","42423"));
        Member savedMember = memberRepository.save(member);

        Article article = Article.builder()
                .member(savedMember)
                .title("제목")
                .content("내용")
                .boardType(BoardType.FOOD)
                .locationX(32.2323)
                .locationY(32.4321312)
                .build();
        articleRepository.save(article);


    }



}