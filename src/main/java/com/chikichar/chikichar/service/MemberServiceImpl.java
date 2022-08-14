package com.chikichar.chikichar.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public Long joinAccount(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member savedMember = memberRepository.save(memberRequestDto.toEntity());
        return savedMember.getId();

    }

    @Override
    public String login(String email, String password) {
//        //TODO Exception 처리 2개 해야함
//        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("email 없음"));
//        if(!passwordEncoder.matches(password, member.getPassword())){
//            throw new IllegalArgumentException("");
//        }
//        return tokenProvider.createToken(member.getEmail(),member.getMemberRole().getKey());
//
        return null;
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
        //TODO 메서드 완성해야함
    }


}
