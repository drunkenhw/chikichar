package com.chikichar.chikichar.config;

import com.chikichar.chikichar.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.chikichar.chikichar.security.filter.TokenAuthenticationFilter;
import com.chikichar.chikichar.security.handler.OAuth2AuthenticationFailureHandler;
import com.chikichar.chikichar.security.handler.OAuth2AuthenticationSuccessHandler;
import com.chikichar.chikichar.security.handler.TokenAccessDeniedHandler;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.security.refreshtoken.UserRefreshTokenRepository;
import com.chikichar.chikichar.security.service.MemberDetailsService;
import com.chikichar.chikichar.security.service.MemberOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppProperties appProperties;
    private final TokenProvider tokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final MemberOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").access("hasRole('USER') or hasRole('ADMIN')");
        http.formLogin();
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository())
            .and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*")
            .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.logout();

        return http.build();
    }
    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                httpCookieOAuth2AuthorizationRequestRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository());
    }


}
