package com.chikichar.chikichar.config;

import com.chikichar.chikichar.security.filter.LocalMemberLoginFilter;
import com.chikichar.chikichar.security.filter.TokenAuthenticationFilter;
import com.chikichar.chikichar.security.handler.LocalMemberSuccessHandler;
import com.chikichar.chikichar.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.chikichar.chikichar.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.chikichar.chikichar.security.handler.TokenAccessDeniedHandler;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.security.service.MemberDetailsService;
import com.chikichar.chikichar.security.oauth.service.MemberOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig  {
    private final AppProperties appProperties;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final MemberDetailsService memberDetailsService;
    private final MemberOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final LocalMemberSuccessHandler localMemberSuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/sample/all",
                        "/",
                        "/login",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/auth/**", "/oauth2/**").permitAll()
                .antMatchers("/sample/member","/api/v1/auth/**").hasRole("USER");

        http.formLogin().disable();

        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
            .and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*")
            .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.exceptionHandling().accessDeniedHandler(tokenAccessDeniedHandler);

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(localMemberLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager);
        http.logout();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider,memberDetailsService);
    }

    @Bean
    public LocalMemberLoginFilter localMemberLoginFilter(AuthenticationManager authenticationManager) throws Exception {
        LocalMemberLoginFilter filter = new LocalMemberLoginFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(localMemberSuccessHandler);
        return filter;
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler();
    }


}
