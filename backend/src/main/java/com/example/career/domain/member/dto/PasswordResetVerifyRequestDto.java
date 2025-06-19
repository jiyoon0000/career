package com.example.career.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordResetVerifyRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
