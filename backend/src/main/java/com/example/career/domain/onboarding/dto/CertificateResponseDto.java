package com.example.career.domain.onboarding.dto;

import lombok.Getter;

@Getter
public class CertificateResponseDto {

    private final String certificateName;

    public CertificateResponseDto(String certificateName) {
        this.certificateName = certificateName;
    }
}
