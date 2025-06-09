package com.example.career.domain.oauth.service;

import com.example.career.domain.member.dto.LoginResponseDto;
import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.domain.oauth.dto.KakaoTokenResponse;
import com.example.career.domain.oauth.dto.KakaoUserInfo;
import com.example.career.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    @Qualifier("kakaoTokenClient")
    private final WebClient kakaoTokenClient;

    @Qualifier("kakaoUserInfoClient")
    private final WebClient kakaoUserInfoClient;

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    public LoginResponseDto kakaoLogin(String code) {
        KakaoTokenResponse tokenResponse = getAccessToken(code);
        KakaoUserInfo userInfo = getUserInfo(tokenResponse.getAccessToken());
        String email = userInfo.getKakaoAccount().getEmail();

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.kakaoUser(email)));

        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    private KakaoTokenResponse getAccessToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        return kakaoTokenClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    private KakaoUserInfo getUserInfo(String accessToken) {
        return kakaoUserInfoClient.get()
                .uri("/v2/user/me")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }
}
