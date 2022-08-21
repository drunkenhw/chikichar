package com.chikichar.chikichar.security.oauth.service;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.Brand;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.SocialType;
import com.chikichar.chikichar.repository.MemberRepository;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.security.oauth.oauthinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return process(userRequest, oAuth2User);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    //TODO 메서드 이름 변경해야함
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        SocialType socialType = getSocialType(userRequest);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.getOAuth2UserInfo(socialType, oAuth2User.getAttributes());

        Member findMember = findMemberOrElseCreateMember(socialType, oAuth2UserInfo);

        if(findMember.getSocialType() != socialType){
            throw new OAuth2AuthenticationException(socialType +"로 가입하신 같은 이메일이 존재합니다. ");
        }

        return UserPrincipal.create(findMember,oAuth2User.getAttributes());
    }

    private Member findMemberOrElseCreateMember(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return memberRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> createMember(oAuth2UserInfo, socialType));
    }

    private SocialType getSocialType(OAuth2UserRequest userRequest) {
        return SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }

    private Member createMember(OAuth2UserInfo userInfo, SocialType socialType) {
        Member user = Member.builder()
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .memberRole(MemberRole.USER)
                .password(UUID.randomUUID().toString())
                .phone(userInfo.getPhone())
                .brand(Brand.ETC)
                .socialType(socialType)
                .build();

        return memberRepository.saveAndFlush(user);
    }



}
