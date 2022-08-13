package com.chikichar.chikichar.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class AuthMemberDto extends User implements OAuth2User {

    private String email;
    private String name;
    private Map<String ,Object> attr;

    public AuthMemberDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
    }

    public AuthMemberDto(String username, String password,String name, Collection<? extends GrantedAuthority> authorities, Map<String ,Object> attr) {
        super(username, password, authorities);
        this.name = name;
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
