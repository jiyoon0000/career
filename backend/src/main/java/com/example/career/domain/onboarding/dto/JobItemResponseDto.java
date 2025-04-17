package com.example.career.domain.onboarding.dto;

import lombok.Getter;

@Getter
public class JobItemResponseDto {

    private final String code;
    private final String name;

    public JobItemResponseDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
