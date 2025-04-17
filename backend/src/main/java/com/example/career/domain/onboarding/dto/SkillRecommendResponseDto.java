package com.example.career.domain.onboarding.dto;

import lombok.Getter;

@Getter
public class SkillRecommendResponseDto {

    private String skillName;

    public SkillRecommendResponseDto(String skillName) {
        this.skillName = skillName;
    }
}
