package com.example.career.domain.member.dto;

import com.example.career.global.util.ValidationPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordResetRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = ValidationPatterns.PASSWORD_REGEX,
            message = "비밀번호는 영문 소문자, 숫자, 특수문자를 포함한 8~12자여야 합니다.")
    private String newPassword;
}
