package com.chikichar.chikichar.controller.member;


import com.chikichar.chikichar.member.dto.JoinForm;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@Valid @RequestBody JoinForm joinForm, BindingResult bindingResult){
        //TODO 유효성 검사 에러처리 해야함
        Long joinMemberId = memberService.joinAccount(joinForm);
        return new ResponseEntity<>(joinMemberId, HttpStatus.OK);
    }


}
