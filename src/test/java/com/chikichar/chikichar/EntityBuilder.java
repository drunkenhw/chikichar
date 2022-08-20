package com.chikichar.chikichar;

import com.chikichar.chikichar.entity.Article;
import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Comment;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EntityBuilder {
    static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static Member createMember(String email, String nickname) {
        return Member.builder()
                .memberRole(MemberRole.USER)
                .email(email+"@naver.com")
                .nickname(nickname)
                .address(new Address("busan","simin","213234"))
                .brand(Brand.BENZ)
                .name("han")
                .password(passwordEncoder.encode("1"))
                .phone("01044444444")
                .build();
    }

    public static Board createBoard() {
        return new Board("benz", BoardType.NORMAL);
    }

    public static Comment createComment(Article article, Member member){
        return new Comment(article, member, "내용");

    }

    public static Article createArticle(Board board , Member member){
        return  Article.builder()
                .board(board)
                .member(member)
                .content("content")
                .title("title")
                .build();
    }
}
