package com.example.career.domain.onboarding.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobSelectionResponseDto {

    private final String jobCode;
    private final String jobName;
}
