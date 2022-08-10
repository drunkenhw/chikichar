package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.MemberRequestDto;
import com.chikichar.chikichar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public Long joinAccount(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member member = memberRequestDto.toEntity();
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
    public void modifyInfo(Long id,MemberRequestDto memberRequestDto) {
        //TODO Exception 처리 해야함
        Member findMember = memberRepository.findById(id).orElseThrow();
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));

        findMember.modifyMember(memberRequestDto);

    }

    @Override
    public boolean isDuplicateEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        return member.isPresent();
    }

    @Override
    public String findEmail(String name, String phone) {
        //TODO 예외처리 해야됨
        return memberRepository.findEmailByNameAndPhone(name, phone).orElseThrow();
    }
}
