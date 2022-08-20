package com.chikichar.chikichar.security.jwt;

import com.chikichar.chikichar.controller.exception.TokenValidFailedException;
import com.chikichar.chikichar.model.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class TokenProvider {

    private final Key key;

    public TokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, MemberRole role , Date expireDate){
        return new AuthToken(id, role, expireDate, key);
    }

    public AuthToken converterAuthToken(String token){
        return new AuthToken(token, key);
    }

}
