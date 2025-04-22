package com.example.career.domain.onboarding.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JacksonXmlRootElement(localName = "dJobDtl")
@Getter
@NoArgsConstructor
public class JobDetailCertificateResponseDto {

    @JacksonXmlProperty(localName = "relCert")
    @JacksonXmlElementWrapper(localName = "relCertList")
    private List<CertItem> relCertList;

    @Getter
    @NoArgsConstructor
    public static class CertItem {
        @JacksonXmlProperty(localName = "certNm")
        private String name;
    }
}
