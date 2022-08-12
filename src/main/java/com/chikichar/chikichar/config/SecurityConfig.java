package com.chikichar.chikichar.config;

import com.chikichar.chikichar.model.MemberRole;
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
//        http.headers().frameOptions().disable()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/","/css/**","/js/**").permitAll()
//                .antMatchers("/api/user/**").hasRole(MemberRole.USER.name())
//                .anyRequest().authenticated();
        return http.build();
    }
}
