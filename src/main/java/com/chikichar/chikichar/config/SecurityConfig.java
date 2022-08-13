package com.chikichar.chikichar.config;

import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.security.handler.LoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
        http.oauth2Login().successHandler(loginSuccessHandler());
        http.logout();

        return http.build();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(passwordEncoder());
    }


//    @Bean
//    public LoginFilter apiLoginFilter(AuthenticationManager authenticationManager) throws Exception {
//        LoginFilter loginFilter = new LoginFilter("/login", jwtProvider);
//        loginFilter.setAuthenticationManager(authenticationManager);
//        return loginFilter;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
