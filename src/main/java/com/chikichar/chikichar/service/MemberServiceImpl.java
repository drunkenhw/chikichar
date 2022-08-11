package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.MemberRequestDto;
import com.chikichar.chikichar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
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
        //TODO Exception custom
        Member findMember = memberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("잘못된 사용자입니다."));
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));

        findMember.modifyMember(memberRequestDto);

    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return memberRepository.existsByEmail(email);

    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public String findEmail(String name, String phone) {
        //TODO Exception Custom
        return memberRepository.findEmailByNameAndPhone(name, phone)
                .orElseThrow(()->new IllegalArgumentException("해당 정보가 존재하지 않습니다."));
    }

    @Override
    public void changePassword(String password) {

    }
}
