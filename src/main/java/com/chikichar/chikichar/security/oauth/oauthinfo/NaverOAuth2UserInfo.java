package com.chikichar.chikichar.security.oauth.oauthinfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
    private final static String RESPONSE = "response";

    public NaverOAuth2UserInfo(Map<String, Object> attr) {
        super(attr);
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attr.get(RESPONSE);
        if (response == null) {
            return null;
        }

        return (String) response.get(EMAIL);
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attr.get(RESPONSE);
        if (response == null) {
            return null;
        }

        return (String) response.get(NAME);
    }


    @Override
    public String getPhone() {
        Map<String, Object> response = (Map<String, Object>) attr.get(RESPONSE);

        if (response == null) {
            return null;
        }

        return (String) response.get("mobile");
    }
}
