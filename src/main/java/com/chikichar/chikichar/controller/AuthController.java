package com.chikichar.chikichar.controller;


import com.chikichar.chikichar.dto.member.LoginRequestDto;
import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.security.refreshtoken.UserRefreshToken;
import com.chikichar.chikichar.security.refreshtoken.UserRefreshTokenRepository;
import com.chikichar.chikichar.security.util.CookieUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        String userEmail = loginRequestDto.getEmail();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userEmail,
                ((UserPrincipal) authentication.getPrincipal()).getMemberRole(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpireDate();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByUserEmail(userEmail);
        if (userRefreshToken.isEmpty()) {
            // 없는 경우 새로 등록

            userRefreshTokenRepository.saveAndFlush(new UserRefreshToken(userEmail, refreshToken.getToken()));
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.get().setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
        HashMap<String ,Object> map = new HashMap<>();
        map.put("token", accessToken.getToken());
        return ResponseEntity.ok(map);
    }

//    @GetMapping("/refresh")
//    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
//        // access token 확인
//        String accessToken ;
//        if (request.getHeader(HEADER_AUTHORIZATION) != null) {
//            String token = request.getHeader(HEADER_AUTHORIZATION).substring(PREFIX.length());
//            accessToken = token;
//        }
//
//        AuthToken authToken = tokenProvider.converterAuthToken(accessToken);
//        if (!authToken.validateToken()) {
//            return ApiResponse.invalidAccessToken();
//        }
//
//        // expired access token 인지 확인
//        Claims claims = authToken.getExpiredTokenClaims();
//        if (claims == null) {
//            return ApiResponse.notExpiredTokenYet();
//        }
//
//        String userEmail = claims.getId();
//        MemberRole role = MemberRole.valueOf(claims.get("role", String.class));
//
//        // refresh token
//        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                .map(Cookie::getValue)
//                .orElse((null));
//        AuthToken authRefreshToken = tokenProvider.converterAuthToken(refreshToken);
//
//        if (authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        // userEmail refresh token 으로 DB 확인
//        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByUserEmailAndRefreshToken(userEmail, refreshToken);
//        if (userRefreshToken == null) {
//            return ApiResponse.invalidRefreshToken();
//        }
//
//        Date now = new Date();
//        AuthToken newAccessToken = tokenProvider.createAuthToken(
//                userEmail,
//                role.getCode(),
//                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
//        );
//
//        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
//
//        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
//        if (validTime <= THREE_DAYS_MSEC) {
//            // refresh 토큰 설정
//            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//
//            authRefreshToken = tokenProvider.createAuthToken(
//                    appProperties.getAuth().getTokenSecret(),
//                    new Date(now.getTime() + refreshTokenExpiry)
//            );
//
//            // DB에 refresh 토큰 업데이트
//            userRefreshToken.setRefreshToken(authRefreshToken.getToken());
//
//            int cookieMaxAge = (int) refreshTokenExpiry / 60;
//            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
//        }
//
//        return ApiResponse.success("token", newAccessToken.getToken());
//    }

}