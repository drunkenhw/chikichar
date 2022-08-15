package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.member.MemberRequestDto;

public interface MemberService {

    String joinAccount(MemberRequestDto memberRequestDto);

    String login(String email, String password);
    void deleteAccount(long memberId);

    void modifyInfo(Long id,MemberRequestDto memberRequestDto);

    boolean isDuplicateEmail(String email);

    boolean isDuplicateNickname(String nickname);

    String findEmail(String name, String phone);

    void changePassword(String password);
}
