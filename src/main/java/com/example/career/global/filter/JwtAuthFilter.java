package com.example.career.global.filter;

import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.error.exception.UnAuthorizedException;
import com.example.career.global.jwt.JwtProvider;
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtProvider.resolveToken(httpServletRequest.getHeader(JwtProvider.AUTH_HEADER));

        try {
            if (token != null) {
                String username = jwtProvider.getUsernameFromToken(token);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
            throw new UnAuthorizedException(ErrorCode.TOKEN_EXPIRED);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT format");
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT token is empty or null");
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        } catch (SecurityException e) {
            log.error("JWT signature does not match");
        } catch (Exception e) {
            log.error("Failed to validate JWT token", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
