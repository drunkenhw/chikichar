package com.chikichar.chikichar.security.oauth.oauthinfo;


import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private static final String PROPERTIES = "properties";
    private static final String NICKNAME = "nickname";
    public KakaoOAuth2UserInfo(Map<String, Object> attr) {
        super(attr);
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attr.get(PROPERTIES);

        if (properties == null) {
            return null;
        }

        return (String) properties.get(NICKNAME);
    }

    @Override
    public String getPhone() {

        return null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attr.get("kakao_account");

        if (properties == null) {
            return null;
        }
        return (String) properties.get(EMAIL);
    }


}
