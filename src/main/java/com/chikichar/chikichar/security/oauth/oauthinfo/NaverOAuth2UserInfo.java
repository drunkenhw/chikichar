package com.chikichar.chikichar.security.oauth.oauthinfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attr) {
        super(attr);
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attr.get("response");
        if (response == null) {
            return null;
        }

        return (String) response.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attr.get("response");
        if (response == null) {
            return null;
        }

        return (String) response.get("name");
    }


    @Override
    public String getPhone() {
        Map<String, Object> response = (Map<String, Object>) attr.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("mobile");
    }
}
