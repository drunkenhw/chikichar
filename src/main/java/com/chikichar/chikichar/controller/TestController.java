package com.chikichar.chikichar.controller;

import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.security.CurrentUser;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;
    @GetMapping("/")
    public String index(){
        return "member/login";
    }
    @GetMapping("/join")
    public String join(@ModelAttribute("form") MemberRequestDto memberRequestDto) {
        return "member/join";
    }
    @PostMapping("/join")
    public String joinMember(MemberRequestDto memberRequestDto){
        log.info("memberRequestDto={}",memberRequestDto);

        memberService.joinAccount(memberRequestDto);
        return "redirect:/";
    }

    @GetMapping("/sample/all")
    public String all(){
        return "sample/all";
    }

    @GetMapping("/sample/member")
    public String member(){
        return "sample/member";
    }
    @GetMapping("/modify")
    public String modify(@ModelAttribute("form")OAuth2MemberRequestDto oAuth2MemberRequestDto) {

        return "member/modify";
    }
    @PostMapping("/modify")
    public String modifyPost(@CurrentUser Member member, @RequestParam OAuth2MemberRequestDto oAuth2MemberRequestDto) {
        memberService.oAuthMemberAddProfile(member, oAuth2MemberRequestDto);
        return "redirect:/";
    }
}
