package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.member.ChangePasswordDto;
import com.chikichar.chikichar.dto.member.MemberResponseDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.entity.Member;

import java.util.List;

public interface MemberService {

    Long joinAccount(MemberRequestDto memberRequestDto);

    void deleteAccount(Member member);

    void modifyInfo(Member member ,MemberRequestDto memberRequestDto);

    boolean isDuplicateEmail(String email);

    boolean isDuplicateNickname(String nickname);

    String findEmail(String name, String phone);

    void changePassword(Member member, ChangePasswordDto changePasswordDto);

    void oAuthMemberAddProfile(Member member, OAuth2MemberRequestDto OAuth2MemberRequestDto);

    List<Member> getMemberList();

    void banMember(Long memberId);

//    MemberResponseDto getMyPage(Member member);

}
