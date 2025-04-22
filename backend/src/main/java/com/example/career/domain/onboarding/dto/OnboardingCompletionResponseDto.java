package com.example.career.domain.onboarding.dto;

import lombok.Getter;

@Getter
public class OnboardingCompletionResponseDto {

    private boolean completed;

    public OnboardingCompletionResponseDto(boolean completed) {
        this.completed = completed;
    }
}
