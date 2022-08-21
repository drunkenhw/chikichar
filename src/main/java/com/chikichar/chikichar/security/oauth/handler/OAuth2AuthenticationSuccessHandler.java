package com.chikichar.chikichar.security.oauth.handler;

import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.SocialType;
import com.chikichar.chikichar.security.UserPrincipal;
import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.oauth.oauthinfo.OAuth2UserInfo;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.security.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //TODO redirect 경로 지정

        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("이미 요청됨 " + targetUrl);
            return;
        }
        log.info("성공");

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, "redirect_uri")
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("유효하지 않은 URL");
        }

        String targetUrl = redirectUri.orElseGet(()->getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        //닉네임이 없으면 개인정보 수정화면으로 넘어감 -> /modify
        if(user.getMember().getNickname() == null){
            targetUrl = "/modify";
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        OAuth2UserInfo userInfo = OAuth2UserInfo.getOAuth2UserInfo(getSocialType(authToken), user.getAttributes());

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userInfo.getEmail(),
                MemberRole.USER,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
    }

    private SocialType getSocialType(OAuth2AuthenticationToken authToken) {
        return SocialType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // 호스트 및 포트만 검증 원하는 경우 다른 경로를 사용하도록 허용
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }


}
