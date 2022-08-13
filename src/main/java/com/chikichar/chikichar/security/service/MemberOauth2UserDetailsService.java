package com.chikichar.chikichar.security.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.dto.AuthMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberOauth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("hi");
        oAuth2User.getAttributes().forEach((k,v)->log.info(k+":"+v));
        String email = null;
        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        //TODO 여기서 MemberRequestDto로 변환해서 저장시키는게 좋을 듯
        String name = oAuth2User.getAttribute("name");
        Member member = saveSocialMember(email,name);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getMemberRole().getKey()));

        AuthMemberDto authMemberDto = new AuthMemberDto(member.getEmail(),member.getPassword(),oAuth2User.getName(),
                authorities,oAuth2User.getAttributes());

        return authMemberDto;
    }

    private Member saveSocialMember(String email, String name) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            return findMember.get();
        }

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .memberRole(MemberRole.USER)
                .build();

        memberRepository.save(member);
        return member;
    }
}
