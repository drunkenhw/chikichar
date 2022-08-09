package com.chikichar.chikichar.controller.member;


import com.chikichar.chikichar.member.dto.MemberRequestDto;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult){
        //TODO 유효성 검사 에러처리 해야함 ,세션도 줘야함
        Long joinMemberId = memberService.joinAccount(memberRequestDto);
        return new ResponseEntity<>(joinMemberId, HttpStatus.OK);
    }

    @GetMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestBody HashMap<String,String> param){
        String email = param.get("email");
        return new ResponseEntity<>(memberService.isDuplicateEmail(email),HttpStatus.OK);
    }
    @GetMapping("/nickname-check")
    public ResponseEntity<Boolean> nicknameCheck(@RequestBody HashMap<String,String > param){
        String nickname = param.get("nickname");
        return new ResponseEntity<>(memberService.isDuplicateNickname(nickname),HttpStatus.OK);
    }





}
