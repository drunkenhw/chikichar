package com.chikichar.chikichar.service;

import com.chikichar.chikichar.member.dto.MemberRequestDto;

public interface MemberService {

    Long joinAccount(MemberRequestDto memberRequestDto);

    void deleteAccount(long memberId);

    void modifyInfo(Long id,MemberRequestDto memberRequestDto);

    boolean isDuplicateEmail(String email);

    boolean isDuplicateNickname(String nickname);
}
