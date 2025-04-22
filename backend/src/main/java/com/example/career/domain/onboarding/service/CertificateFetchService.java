package com.example.career.domain.onboarding.service;

import com.example.career.domain.onboarding.dto.CertificateResponseDto;
import com.example.career.domain.onboarding.dto.JobDetailCertificateResponseDto;
import com.example.career.domain.onboarding.entity.Certificate;
import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.repository.CertificateRepository;
import com.example.career.domain.onboarding.repository.JobRepository;
import com.example.career.global.client.CertApiClient;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.ExternalApiException;
import com.example.career.global.error.exception.NotFoundException;
import com.example.career.global.util.XmlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateFetchService {

    private final CertApiClient certApiClient;
    private final CertificateRepository certificateRepository;
    private final JobRepository jobRepository;

    public List<CertificateResponseDto> fetchAndSaveCertificates(String jobCode) {

        Job job = jobRepository.findByCode(jobCode)
                .orElseThrow(() -> new NotFoundException(ErrorCode.JOB_NOT_FOUND));

        List<Certificate> existingCertificates = certificateRepository.findAllByJob(job);
        if (!existingCertificates.isEmpty()) {
            return existingCertificates.stream()
                    .map(cert -> new CertificateResponseDto(cert.getName()))
                    .toList();
        }

        String xml = certApiClient.getCertificatesByJobCode(jobCode).block();

        if (xml == null || xml.isBlank()) {
            throw new ExternalApiException("고용24 API 응답이 비어있습니다.");
        }

        JobDetailCertificateResponseDto jobDetailCertificateResponseDto = XmlUtil.fromXml(xml, JobDetailCertificateResponseDto.class);

        if (jobDetailCertificateResponseDto.getRelCertList() == null || jobDetailCertificateResponseDto.getRelCertList().isEmpty()) {
            return List.of();
        }

        List<CertificateResponseDto> savedCerts = new ArrayList<>();
        for (JobDetailCertificateResponseDto.CertItem item : jobDetailCertificateResponseDto.getRelCertList()) {

            if (!certificateRepository.existsByNameAndJob(item.getName(), job)){
                certificateRepository.save(new Certificate(item.getName(), job));
                savedCerts.add(new CertificateResponseDto(item.getName()));
            }
        }

        return savedCerts;
    }
}
