package com.example.career.global.filter;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.response.ErrorResponse;
import com.example.career.global.jwt.JwtProvider;
import com.example.career.global.security.MemberDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtProvider.resolveToken(httpServletRequest.getHeader(JwtProvider.AUTH_HEADER));

        try {
            if (token != null) {
                jwtProvider.validateTokenOrThrow(token);
                String email = jwtProvider.getUsernameFromToken(token);

                Member member = memberRepository.findByEmail(email)
                        .orElse(null);

                if (member == null) {
                    log.warn("Member Not Found: {}", email);
                    setErrorResponse(httpServletResponse, ErrorCode.MEMBER_NOT_FOUND, httpServletRequest);
                    return;
                }

                MemberDetails memberDetails = new MemberDetails(member);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
            setErrorResponse(httpServletResponse, ErrorCode.TOKEN_EXPIRED, httpServletRequest);
            return;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT format");
            setErrorResponse(httpServletResponse, ErrorCode.INVALID_TOKEN, httpServletRequest);
            return;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            setErrorResponse(httpServletResponse, ErrorCode.INVALID_TOKEN, httpServletRequest);
            return;
        } catch (IllegalArgumentException e) {
            log.error("JWT token is empty or null");
            setErrorResponse(httpServletResponse, ErrorCode.INVALID_TOKEN, httpServletRequest);
            return;
        } catch (SecurityException e) {
            log.error("JWT signature does not match");
            setErrorResponse(httpServletResponse, ErrorCode.INVALID_TOKEN, httpServletRequest);
            return;
        } catch (Exception e) {
            log.error("Failed to validate JWT token", e);
            setErrorResponse(httpServletResponse, ErrorCode.INTERNAL_SERVER_ERROR, httpServletRequest);
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setErrorResponse(HttpServletResponse httpServletResponse, ErrorCode errorCode, HttpServletRequest httpServletRequest) throws IOException {

        httpServletResponse.setStatus(errorCode.getStatus().value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(
                errorCode,
                httpServletRequest.getRequestURI(),
                httpServletRequest.getMethod()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = objectMapper.writeValueAsString(errorResponse);
        httpServletResponse.getWriter().write(json);
        httpServletResponse.flushBuffer();
    }
}
