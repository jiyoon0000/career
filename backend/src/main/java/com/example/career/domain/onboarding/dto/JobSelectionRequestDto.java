package com.example.career.domain.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobSelectionRequestDto {

    @NotBlank(message = "직업 코드는 필수입니다.")
    private String jobCode;

    public JobSelectionRequestDto(String jobCode) {
        this.jobCode = jobCode;
    }
}
