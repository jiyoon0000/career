package com.example.career.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;
}
