//package com.chikichar.chikichar.security.filter;
//
//import com.chikichar.chikichar.dto.member.MemberRequestDto;
//import com.chikichar.chikichar.security.jwt.JwtProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//public class LoginFilter extends AbstractAuthenticationProcessingFilter {
//
////    private JwtProvider jwtProvider;
//
//    public LoginFilter(String defaultFilterProcessesUrl, JwtProvider jwtProvider) {
//        super(defaultFilterProcessesUrl);
//        this.jwtProvider = jwtProvider;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        log.info("login Filter init");
//
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
//
//        return getAuthenticationManager().authenticate(authToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        log.info("successfulAuthentication ={}",authResult.getPrincipal());
//
//        String email = ((MemberRequestDto) authResult.getPrincipal()).getEmail();
//        String role = ((MemberRequestDto) authResult.getPrincipal()).getMemberRole().getValue();
//        String token = null;
//
//        try {
//            token = jwtProvider.createToken(email,role);
//            response.setContentType("text/plain");
//            response.getOutputStream().write(token.getBytes());
//            log.info("token ={}",token);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
