package com.example.career.domain.onboarding.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SkillSelectionRequestDto {

    @NotEmpty(message = "스킬은 최소 1개 이상 선택해야 합니다.")
    @Size(max = 10, message = "최대 10개의 스킬만 선택할 수 있습니다.")
    private List<String> skills;
}
