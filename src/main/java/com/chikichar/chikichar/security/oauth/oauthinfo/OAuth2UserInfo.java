package com.chikichar.chikichar.security.oauth.oauthinfo;

import com.chikichar.chikichar.model.SocialType;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class OAuth2UserInfo {

    protected Map<String ,Object> attr;
    protected final static String EMAIL = "email";
    protected final static String NAME = "name";

    public OAuth2UserInfo(Map<String, Object> attr) {
        this.attr = attr;
    }

    public abstract String getEmail();
    public abstract String getName();
    public abstract String getPhone();

    public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attr) {
        switch (socialType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attr);
            case NAVER: return new NaverOAuth2UserInfo(attr);
            case KAKAO: return new KakaoOAuth2UserInfo(attr);
            default: throw new IllegalArgumentException("소셜 로그인 지원 불가");
        }
    }

}
