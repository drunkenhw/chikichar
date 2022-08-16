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

    private static final String HTTP_METHOD = "POST";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", HTTP_METHOD);
    private final ObjectMapper objectMapper;

    public LocalMemberLoginFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }


    @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
            log.info("JsonUsernamePasswordAuthenticationFilter 이 동작합니다!!!");

            if (!request.getMethod().equals(HTTP_METHOD) || !request.getContentType().equals("application/json")) {
                log.error("POST 요청이 아니거나 JSON이 아닙니다!");
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }

        LoginRequestDto loginRequestDto = objectMapper
                .readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharset.UTF_8), LoginRequestDto.class);


        String username = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

            if(username ==null || password == null){
                throw new AuthenticationServiceException("DATA IS MISS");
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            return super.getAuthenticationManager().authenticate(authRequest);//getAuthenticationManager를 커스텀해줌
        }

}
