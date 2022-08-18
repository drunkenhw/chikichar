package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.repository.MemberRepository;
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

    @Transactional
    @Override
    public Long joinAccount(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        Member savedMember = memberRepository.save(memberRequestDto.toEntity());
        return savedMember.getId();


    }


    @Transactional
    @Override
    public void deleteAccount(Member member) {
        memberRepository.deleteById(member.getId());
    }

    @Transactional
    @Override
    public void modifyInfo(Member member,MemberRequestDto memberRequestDto) {
        //TODO Exception custom

        member.changePassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        member.modifyMember(memberRequestDto.getNickname(),memberRequestDto.getPhone(),
                memberRequestDto.getAddress(),memberRequestDto.getBrand());
        memberRepository.save(member);


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

    @Transactional
    @Override
    public void changePassword(Member member,MemberRequestDto requestDto) {
        //TODO 메서드 완성해야함
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void oAuthMemberAddProfile(Member member, OAuth2MemberRequestDto OAuth2MemberRequestDto) {

        //TODO 메서드 완성해야함
        member.modifyMember(
                OAuth2MemberRequestDto.getNickname(),
                OAuth2MemberRequestDto.getPhone(),
                new Address(OAuth2MemberRequestDto.getCity(),OAuth2MemberRequestDto.getStreet(),OAuth2MemberRequestDto.getZipcode()),
                OAuth2MemberRequestDto.getBrand());
        memberRepository.save(member);
    }


}
