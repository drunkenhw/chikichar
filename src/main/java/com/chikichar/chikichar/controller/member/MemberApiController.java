package com.chikichar.chikichar.controller.member;


import com.chikichar.chikichar.dto.MemberRequestDto;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @PostMapping("/join")
    public Long join(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult){
        //TODO 유효성 검사 에러처리 해야함 ,세션도 줘야함
        Long joinMemberId = memberService.joinAccount(memberRequestDto);
        return joinMemberId;
    }

    @GetMapping(value = "/email-check")
    public Boolean emailCheck(@RequestBody HashMap<String,String> param){
        String email = param.get("email");
        return memberService.isDuplicateEmail(email);
    }
    @GetMapping("/nickname-check")
    public Boolean nicknameCheck(@RequestBody HashMap<String,String > param){
        String nickname = param.get("nickname");
        return memberService.isDuplicateNickname(nickname);
    }

    @PostMapping("/findEmail")
    public Map<String ,String> findEmail(@RequestBody Map<String, String> param){
        Map<String ,String> result = new HashMap<>();
        String email = memberService.findEmail(param.get("name"), param.get("phone"));
        result.put("email",email);
        return result;
    }




}
