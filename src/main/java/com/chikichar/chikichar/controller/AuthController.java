package com.chikichar.chikichar.controller;


import com.chikichar.chikichar.dto.member.LoginRequestDto;
import com.chikichar.chikichar.dto.member.LoginResponseDto;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;


}
