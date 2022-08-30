package com.chikichar.chikichar.controller.member;


import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;



    @GetMapping(value = "/email-check")
    public Boolean emailCheck(String email){
        log.info("email={}" ,email);
        return memberService.isDuplicateEmail(email);
    }

    @GetMapping("/nickname-check")
    public Boolean nicknameCheck(String nickname){
        log.info("nickname={}",nickname);
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
