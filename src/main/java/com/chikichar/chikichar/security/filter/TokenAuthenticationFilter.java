package com.chikichar.chikichar.security.filter;

import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAccessToken(request);
        AuthToken authToken = tokenProvider.converterAuthToken(token);

            log.info("token임={}",token);
        if(authToken.validateToken()){
            Authentication authentication = tokenProvider.getAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String getAccessToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_AUTHORIZATION) != null) {
            String token = request.getHeader(HEADER_AUTHORIZATION).substring(PREFIX.length());
            return token;
        }
        return null;
    }
}
