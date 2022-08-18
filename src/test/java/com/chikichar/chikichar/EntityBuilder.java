package com.chikichar.chikichar;

import com.chikichar.chikichar.entity.Board;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.BoardType;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;

public class EntityBuilder {
    public static Member createMember() {
        Member member = Member.builder()
                .memberRole(MemberRole.USER)
                .email("before1@naver.com")
                .nickname("beforeNickname1")
                .address(new Address("busan","simin","213234"))
                .brand(Brand.BENZ)
                .name("before")
                .password("secret")
                .phone("01044443333")
                .build();
        return member;
    }

    public static Board createBoard() {
        Board board = new Board("benz", BoardType.NORMAL);
        return board;
    }
}
