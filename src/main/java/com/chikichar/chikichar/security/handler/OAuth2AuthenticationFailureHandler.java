package com.chikichar.chikichar.security.handler;

import com.chikichar.chikichar.security.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrlFromCookie = CookieUtil.getCookie(request, "redirect_uri")
                .map(Cookie::getValue)
                .orElseGet(()->"/sample/member");

        exception.printStackTrace();

        String targetUrl = UriComponentsBuilder.fromUriString(targetUrlFromCookie)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();


        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
