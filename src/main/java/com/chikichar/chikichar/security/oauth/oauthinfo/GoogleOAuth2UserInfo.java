package com.chikichar.chikichar.security.oauth.oauthinfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String ,Object> attr){
        super(attr);
    }

    @Override
    public String getEmail() {
        return (String) attr.get("email");
    }

    @Override
    public String getName() {
        return (String) attr.get("name");
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }
}
