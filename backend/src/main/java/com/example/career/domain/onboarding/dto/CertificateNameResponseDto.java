package com.example.career.domain.onboarding.dto;

import com.example.career.domain.onboarding.entity.Certificate;
import lombok.Getter;

@Getter
public class CertificateNameResponseDto {

    private final String certificateName;

    public CertificateNameResponseDto(Certificate certificate) {
        this.certificateName = certificate.getName();
    }
}
