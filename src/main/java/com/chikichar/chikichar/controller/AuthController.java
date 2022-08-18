package com.chikichar.chikichar.controller;


import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.security.CurrentUser;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PatchMapping("/me")
    public String  oAuthMemberModify(@CurrentUser Member member,@RequestBody OAuth2MemberRequestDto oAuth2MemberRequestDto){

        memberService.oAuthMemberAddProfile(member,oAuth2MemberRequestDto);

        return member.toString();
    }
    @PatchMapping("/change-password")
    public String changePassword(@CurrentUser Member member,
                                 @RequestBody MemberRequestDto memberRequestDto){
        memberService.changePassword(member,memberRequestDto);
        return "good";
    }

}
