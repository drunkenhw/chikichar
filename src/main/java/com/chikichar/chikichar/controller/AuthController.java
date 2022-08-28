package com.chikichar.chikichar.controller;


import com.chikichar.chikichar.dto.member.ChangePasswordDto;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.dto.member.MemberResponseDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.security.CurrentUser;
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

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> myPage(@CurrentUser Member member) {
        return ResponseEntity.ok().body(new MemberResponseDto());
    }

    @PatchMapping("/me")
    public ResponseEntity oAuthMemberModify(@CurrentUser Member member, @RequestBody OAuth2MemberRequestDto oAuth2MemberRequestDto) {

        memberService.oAuthMemberAddProfile(member, oAuth2MemberRequestDto);

        return ResponseEntity.ok().body("회원 정보 수정 완료");
    }

    @PatchMapping("/change-password")
    public ResponseEntity changePassword(@CurrentUser Member member,
                                         @RequestBody ChangePasswordDto changePasswordDto) {
        memberService.changePassword(member, changePasswordDto);
        return ResponseEntity.ok().body("비밀번호 변경 완료");
    }


}
