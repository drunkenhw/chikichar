package com.chikichar.chikichar.service;

import com.chikichar.chikichar.member.dto.JoinForm;
import com.chikichar.chikichar.member.dto.ModifyForm;

public interface MemberService {

    Long joinAccount(JoinForm joinForm);

    void deleteAccount(long memberId);

    void modifyInfo(ModifyForm modifyForm);


}
