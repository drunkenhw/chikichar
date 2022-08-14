package com.chikichar.chikichar.security.handler;

import com.chikichar.chikichar.model.MemberRole;
import com.chikichar.chikichar.model.Social;
import com.chikichar.chikichar.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.chikichar.chikichar.security.jwt.AuthToken;
import com.chikichar.chikichar.security.jwt.TokenProvider;
import com.chikichar.chikichar.security.oauthinfo.OAuth2UserInfo;
import com.chikichar.chikichar.security.properties.AppProperties;
import com.chikichar.chikichar.security.refreshtoken.UserRefreshToken;
import com.chikichar.chikichar.security.refreshtoken.UserRefreshTokenRepository;
import com.chikichar.chikichar.security.util.CookieUtil;
import lombok.RequiredArgsConstructor;
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
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static com.chikichar.chikichar.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.chikichar.chikichar.security.HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("이미 요청됨 " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("유효하지 않은 URL");
        }

        String targetUrl = redirectUri.orElse("/");

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        Social social = Social.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = (OidcUser) authentication.getPrincipal();
        OAuth2UserInfo userInfo = OAuth2UserInfo.getOAuth2UserInfo(social, user.getAttributes());


        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userInfo.getEmail(),
                MemberRole.USER,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // refresh 토큰 설정
        long refreshTokenExpireDate = appProperties.getAuth().getRefreshTokenExpireDate();

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpireDate)
        );

        // DB 저장
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByUserEmail(userInfo.getEmail());
        if (userRefreshToken.isPresent()) {
            userRefreshToken.get().setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshTokenRepository.saveAndFlush(new UserRefreshToken(userInfo.getEmail(), refreshToken.getToken()));
        }

        int cookieMaxAge = (int) refreshTokenExpireDate / 60;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
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

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
