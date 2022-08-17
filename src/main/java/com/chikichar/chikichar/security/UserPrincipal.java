package com.chikichar.chikichar.security;

import com.chikichar.chikichar.entity.Member;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.SocialType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {

//    private final String email;
//    private final String password;
//    private final SocialType socialType;
//    private final MemberRole memberRole;
//    private final Collection<GrantedAuthority> authorities;
    private Member member;
    private Map<String, Object> attr;


    private UserPrincipal(Member member) {
        this.member = member;
    }

    public static UserPrincipal create(Member member) {
        return new UserPrincipal(member);
    }
    public static UserPrincipal create(Member member, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(member);
        userPrincipal.setAttr(attributes);

        return userPrincipal;
    }

    public Member getMember(){
        return member;
    }

    public void setAttr(Map<String, Object> attr){
        this.attr = attr;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attr;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(MemberRole.USER.getKey()));

    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getName() {
        return member.getName();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }



}
