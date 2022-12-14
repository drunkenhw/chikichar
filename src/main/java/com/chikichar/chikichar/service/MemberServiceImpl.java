package com.chikichar.chikichar.service;

import com.chikichar.chikichar.dto.member.ChangePasswordDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
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
    public void modifyInfo(Member member, MemberRequestDto memberRequestDto) {
        //TODO Exception custom

        member.changePassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        member.modifyMember(
                memberRequestDto.getNickname(), memberRequestDto.getPhone(),
                memberRequestDto.getAddress(), memberRequestDto.getBrand()
                ,MemberRole.valueOf(memberRequestDto.getMemberRole()));

//        memberRepository.save(member);


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
                .orElseThrow(() -> new IllegalArgumentException("?????? ????????? ???????????? ????????????."));
    }

    @Transactional
    @Override
    public void changePassword(Member member, ChangePasswordDto changePasswordDto) {
        if (isNotMatchPassword(member, changePasswordDto)) {
            throw new IllegalArgumentException("?????? ??????????????? ?????? ????????????");
        }
        member.changePassword(passwordEncoder.encode(changePasswordDto.getChangePassword()));
//        memberRepository.save(member);
    }

    private boolean isNotMatchPassword(Member member, ChangePasswordDto changePasswordDto) {
        return !passwordEncoder.matches(changePasswordDto.getCurrentPassword(), member.getPassword());
    }

    @Transactional
    @Override
    public void oAuthMemberAddProfile(Member member, OAuth2MemberRequestDto OAuth2MemberRequestDto) {

        member.OAuth2ModifyMember(
                OAuth2MemberRequestDto.getNickname(),
                OAuth2MemberRequestDto.getPhone(),
                new Address(OAuth2MemberRequestDto.getCity(), OAuth2MemberRequestDto.getStreet(), OAuth2MemberRequestDto.getZipcode()),
                OAuth2MemberRequestDto.getBrand(),
                MemberRole.USER);

//        memberRepository.save(member);

    }

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Transactional
    @Override
    public void banMember(Long memberId) {
        Member banMember = memberRepository.findById(memberId).orElseThrow(
                //TODO Exception ?????? ?????????
                () -> new NoSuchElementException()
        );
        banMember.ban();
    }

//    @Override
//    public MemberResponseDto getMyPage(Member member) {
//
//        return
//    }
}
