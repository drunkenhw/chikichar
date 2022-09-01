package com.chikichar.chikichar.service;

import com.chikichar.chikichar.EntityBuilder;
import com.chikichar.chikichar.dto.member.ChangePasswordDto;
import com.chikichar.chikichar.dto.member.OAuth2MemberRequestDto;
import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.dto.member.MemberRequestDto;
import com.chikichar.chikichar.model.Address;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void createMember(){
        Member member = EntityBuilder.createMember("before","before");
        memberRepository.save(member);
    }




    @Test
    @DisplayName("회원가입, 탈퇴 테스트")
    void joinWithdrawalTest(){
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .city("b")
                .street("b")
                .zipcode("b")
                .brand(Brand.AUDI)
                .memberRole("USER")
                .email("aaa@naver.com")
                .nickname("aa")
                .password("aaaa")
                .phone("101010100")
                .name("han")
                .build();
        //가입 테스트
        Long saveMemberId = memberService.joinAccount(memberRequestDto);
        Optional<Member> saveMember = memberRepository.findById(saveMemberId);
        assertThat(saveMember.isPresent()).isEqualTo(true);

        //탈퇴 테스트

        memberService.deleteAccount(saveMember.get());
        Optional<Member> deleteMember = memberRepository.findById(saveMemberId);
        assertThat(deleteMember.isPresent()).isEqualTo(false);
    }
    @Test
    @DisplayName("DTO로 회원 정보를 수정 한다.")
    void modifyTest()  {
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .city("b")
                .street("b")
                .zipcode("b")
                .brand(Brand.AUDI)
                .memberRole("USER")
                .email("old@naver.com")
                .nickname("old")
                .password("bbbb")
                .phone("01044443333")
                .name("old")
                .build();

        Long saveMemberId = memberService.joinAccount(memberRequestDto);

        MemberRequestDto modifyMember = MemberRequestDto.builder()
                .name("han")
                .city("b")
                .street("b")
                .zipcode("b")
                .brand(Brand.BMW)
                .memberRole("USER")
                .nickname("change")
                .password("aaaa")
                .phone("01033334444")
                .build();

        System.out.println(saveMemberId);

        Member member = memberRepository.findById(saveMemberId).orElseThrow();
        memberService.modifyInfo(member,modifyMember);

        assertThat(member.getNickname()).isEqualTo("change");
    }

    @Test
    @DisplayName("이메일이 중복되면 true를 반환한다.")
    void emailDuplicateTest(){

        boolean duplicateEmail = memberService.isDuplicateEmail("before@naver.com");

        assertThat(duplicateEmail).isEqualTo(true);
    }

//    @Test
//    @DisplayName("전화번호, 이름으로 이메일을 반환한다.")
//    void findEmailTest(){
//        String han = memberService.findEmail("han", "01044444444");
//
//        assertThat(han).isEqualTo("before@naver.com");
//    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    void changePasswordTest(){
        Member member = memberRepository.findByEmail("before@naver.com").orElseThrow();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("1","2");
        memberService.changePassword(member, changePasswordDto);
        assertThat(passwordEncoder.matches("2",member.getPassword())).isTrue();
    }
    @Test
    @DisplayName("현재 비밀번호가 맞지않으면 Exception이 발생한다.")
    void changePasswordExceptionTest(){
        Member member = memberRepository.findByEmail("before@naver.com").orElseThrow();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("2","3");
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> memberService.changePassword(member, changePasswordDto));
    }

    @Test
    @DisplayName("Member의 전체 리스트를 조회한다.")
    public void getMemberList(){
        List<Member> memberList = memberService.getMemberList();
        for (Member member : memberList) {
            System.out.println("member = " + member);
        }
    }

    @Test
    @DisplayName("소셜 로그인 회원정보를 추가한다.")
    public void modifyOAuth2(){
        Member member = memberRepository.findByEmail("before@naver.com").orElseThrow();
        OAuth2MemberRequestDto oAuth2MemberRequestDto = OAuth2MemberRequestDto.builder()
                .brand(Brand.CHEVROLET).city("seoul").name("kim").phone("01021010101")
                .street("gangnam").nickname("kent").zipcode("1233").build();
        memberService.oAuthMemberAddProfile(member,oAuth2MemberRequestDto);
        assertThat(member.getAddress().getCity()).isEqualTo("seoul");
    }

    @Test
    @DisplayName("회원을 정지시키면 MemberRole이 BAN이 된다.")
    void bannedMember(){
        Member member = memberRepository.findByEmail("before@naver.com").orElseThrow();
        memberService.banMember(member.getId());
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.BAN);
    }
}