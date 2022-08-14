package com.chikichar.chikichar.security.oauthinfo;

import java.util.Map;

public class NaverOAuth2MemberInfo extends OAuth2MemberInfo{

    public NaverOAuth2MemberInfo(Map<String, Object> attr) {
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
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attr.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("nickname");
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
