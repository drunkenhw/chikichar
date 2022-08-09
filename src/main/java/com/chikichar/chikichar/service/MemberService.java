package com.chikichar.chikichar.service;

import com.chikichar.chikichar.member.dto.JoinForm;
import com.chikichar.chikichar.member.dto.ModifyForm;

public interface MemberService {

    Long signIn(JoinForm joinForm);

    //TODO 변수명 바꿔야함
    void signOut(long memberId);

    void modifyInfo(ModifyForm modifyForm);
}
