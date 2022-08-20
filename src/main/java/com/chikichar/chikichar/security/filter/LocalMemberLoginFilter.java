package com.chikichar.chikichar.security.filter;

import com.chikichar.chikichar.dto.member.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.StandardCharset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LocalMemberLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String HTTP_POST = "POST";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", HTTP_POST);
    private final ObjectMapper objectMapper;

    public LocalMemberLoginFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }


    @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
            log.info("LocalMemberLoginFilter !!!");

            if (!request.getMethod().equals(HTTP_POST) || !request.getContentType().equals("application/json")) {
                log.info("POST 요청이 아니거나 JSON이 아닙니다!");
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }

        LoginRequestDto loginRequestDto = objectMapper
                .readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharset.UTF_8), LoginRequestDto.class);

        String username = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

            if(username ==null || password == null){
                throw new AuthenticationServiceException("아이디, 혹은 패스워드를 입력하세요");
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return super.getAuthenticationManager().authenticate(authRequest);
        }

}
