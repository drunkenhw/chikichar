package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.MemberRequestDto;

public interface MemberService {

    Long joinAccount(MemberRequestDto memberRequestDto);

    void deleteAccount(long memberId);

    void modifyInfo(Long id,MemberRequestDto memberRequestDto);

    boolean isDuplicateEmail(String email);

    boolean isDuplicateNickname(String nickname);

    String findEmail(String name, String phone);
}
