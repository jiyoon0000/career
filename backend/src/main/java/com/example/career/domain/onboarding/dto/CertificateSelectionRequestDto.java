package com.example.career.domain.onboarding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CertificateSelectionRequestDto {

    private List<String> certificateNames;
}
