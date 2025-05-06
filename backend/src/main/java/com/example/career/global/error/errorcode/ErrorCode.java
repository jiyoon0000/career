package com.example.career.global.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // BAD_REQUEST
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    JOB_NOT_SELECTED(HttpStatus.BAD_REQUEST, "선택된 직무가 없습니다."),
    INVALID_SKILL_COUNT(HttpStatus.BAD_REQUEST, "스킬은 최소 1개 이상, 최대 10개까지 선택할 수 있습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않았습니다."),

    // NOT_FOUND
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 직업을 찾을 수 없습니다."),

    // CONFLICT
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    // UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    // FORBIDDEN
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    public HttpStatus getHttpStatus() {
        return status;
    }
}
