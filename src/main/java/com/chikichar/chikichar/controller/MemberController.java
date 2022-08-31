package com.chikichar.chikichar.controller;

import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.security.CurrentUser;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/role-select")
    public String roleSelectForm(){
        return "member/select-role";
    }

    @GetMapping("/join")
    public String join(@RequestParam String role,
            @ModelAttribute("form") MemberRequestDto memberRequestDto) {
        if(role.equals("company")) {
            return "member/company-join";
        }
        return "member/user-join";

    }


    @PostMapping("/join")
    public String joinMember(@Valid MemberRequestDto memberRequestDto){
        log.info("memberRequestDto={}",memberRequestDto);

        memberService.joinAccount(memberRequestDto);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "member/login";
    }



    @GetMapping("/modify")
    public String modify(@CurrentUser  Member member,
                         @ModelAttribute("form")OAuth2MemberRequestDto oAuth2MemberRequestDto) {
        oAuth2MemberRequestDto.setName(member.getName());

        return "member/oauthModify";
    }

    @PostMapping("/modify")
    public String modifyPost(@CurrentUser Member member,
                             @Valid OAuth2MemberRequestDto oAuth2MemberRequestDto) {
        log.info("oAuth2MemberRequestDto={}",oAuth2MemberRequestDto);
        memberService.oAuthMemberAddProfile(member, oAuth2MemberRequestDto);
        return "redirect:/";
    }
}
