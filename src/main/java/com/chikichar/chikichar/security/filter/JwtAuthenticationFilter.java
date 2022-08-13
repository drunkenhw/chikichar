//package com.chikichar.chikichar.security.filter;
//
//import com.chikichar.chikichar.security.jwt.JwtProvider;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilter {
//
////    private final JwtProvider jwtProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token = jwtProvider.resolveToken((HttpServletRequest) request);
//        if (token != null && jwtProvider.validateToken(token)) {
//            Authentication authentication = jwtProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info("jwt Token validate");
//        }
//        chain.doFilter(request, response);
//    }
//}
