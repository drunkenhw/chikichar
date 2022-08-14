package com.chikichar.chikichar.security.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.Social;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.security.oauthinfo.OAuth2MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return this.process(userRequest, oAuth2User);
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        Social social = Social.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2MemberInfo oAuth2UserInfo = OAuth2MemberInfo.getOAuth2UserInfo(social, oAuth2User.getAttributes());

        Member findMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElse(createMember(oAuth2UserInfo, social));

        if(findMember.getSocial() != social){
            throw new RuntimeException(social+"로 가입하신 같은 아이디가 존재합니다. ");
        }

        return UserPrincipal.create(findMember,oAuth2User.getAttributes());
    }



    private void isExistMemberWithOtherSocial(Social social, Member findMember) {

    }

    private Member createMember(OAuth2MemberInfo userInfo, Social social) {
        Member user = Member.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .memberRole(MemberRole.USER)
                .nickname(userInfo.getNickname())
                .password(UUID.randomUUID().toString())
                .phone(userInfo.getPhone())
                .social(social)
                .build();

        return memberRepository.saveAndFlush(user);
    }



}
