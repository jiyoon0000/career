package com.example.career.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SIGNUP_SUCCESS("회원가입이 완료되었습니다."),
    LOGIN_SUCCESS("로그인이 완료되었습니다."),
    LOGOUT_SUCCESS("로그아웃이 완료되었습니다."),
    PASSWORD_CHANGE_SUCCESS("비밀번호가 변경되었습니다."),
    CREATE_SUCCESS("생성이 완료되었습니다."),
    FETCH_SUCCESS("성공적으로 조회되었습니다."),
    UPDATE_SUCCESS("성공적으로 수정되었습니다."),
    DELETE_SUCCESS("성공적으로 삭제되었습니다.");

    private final String message;
}
