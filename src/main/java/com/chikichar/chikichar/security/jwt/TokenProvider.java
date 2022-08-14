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
    private static final String AUTHORITIES_KEY = "role";


    public TokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, Date expireDate) {
        return new AuthToken(id, expireDate, key);
    }

    public AuthToken createAuthToken(String id, MemberRole role , Date expireDate){
        return new AuthToken(id, role, expireDate, key);
    }

    public AuthToken converterAuthToke(String token){
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken){
        if(authToken.validateToken()){
            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toList());
            User user = new User(claims.getSubject(), "",authorities);
            return new UsernamePasswordAuthenticationToken(user, authToken, authorities);
        }else {
            throw new TokenValidFailedException();
        }
    }
}
