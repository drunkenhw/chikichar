package com.chikichar.chikichar.controller.member;


import com.chikichar.chikichar.dto.MemberRequestDto;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
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

    @PostMapping("/join")
    public Long join(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) throws BindException {
        //TODO 세션도 줘야함
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Long joinMemberId = memberService.joinAccount(memberRequestDto);
        return joinMemberId;
    }

    @GetMapping(value = "/email-check")
    public Boolean emailCheck(String email){
        return memberService.isDuplicateEmail(email);
    }
    @GetMapping("/nickname-check")
    public Boolean nicknameCheck(String nickname){
        return memberService.isDuplicateNickname(nickname);
    }

    @GetMapping("/find-email")
    public Map<String ,String> findEmail(String name, String phone){
        Map<String ,String> result = new HashMap<>();
        String email = memberService.findEmail(name, phone);
        result.put("email",email);
        return result;
    }




}
