package com.chikichar.chikichar.security.handler;

import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalMemberSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //TODO redirect 경로 지정
        OidcUser principal = (OidcUser) authentication.getPrincipal();

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                principal.getEmail(),
                MemberRole.USER,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );
        response.setHeader(AUTHORIZATION, TOKEN_PREFIX+accessToken.getToken());
    }
}
