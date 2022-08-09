package com.chikichar.chikichar.service;

import com.chikichar.chikichar.member.domain.Member;
import com.chikichar.chikichar.member.dto.JoinForm;
import com.chikichar.chikichar.member.dto.ModifyForm;
import com.chikichar.chikichar.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long joinAccount(JoinForm joinForm) {
        //TODO password Encoding 해야함
        Member member = Member.builder()
                .email(joinForm.getEmail())
                .password(joinForm.getPassword())
                .name(joinForm.getName())
                .address(joinForm.getAddress())
                .nickname(joinForm.getNickname())
                .phone(joinForm.getPhone())
                .brand(joinForm.getBrand())
                .memberRole(joinForm.getMemberRole())
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();

    }

    @Transactional
    @Override
    public void deleteAccount(long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    @Override
    public void modifyInfo(ModifyForm modifyForm) {
        //TODO Exception 처리 해야함
        Member findMember = memberRepository.findById(modifyForm.getId()).orElseThrow();
        findMember.modifyMember(modifyForm.getPassword(), modifyForm.getNickname(),
                modifyForm.getAddress(), modifyForm.getPhone(), modifyForm.getBrand());

    }
}
