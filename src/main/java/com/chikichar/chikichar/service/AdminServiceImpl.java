package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();

    }

}
