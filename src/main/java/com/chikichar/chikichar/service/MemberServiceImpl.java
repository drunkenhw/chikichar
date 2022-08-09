package com.chikichar.chikichar.service;

import com.chikichar.chikichar.member.domain.Member;
import com.chikichar.chikichar.member.dto.MemberRequestDto;
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
    public Long joinAccount(MemberRequestDto memberRequestDto) {
        //TODO password Encoding 해야함
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
}
