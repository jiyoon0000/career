package com.example.career.domain.onboarding.service;

import com.example.career.domain.onboarding.dto.JobDictionaryResponseDto;
import com.example.career.domain.onboarding.dto.JobItemResponseDto;
import com.example.career.global.client.JobApiClient;
import com.example.career.global.error.exception.ExternalApiException;
import com.example.career.global.util.XmlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobApiClient jobApiClient;

    public List<JobItemResponseDto> searchJobs(String keyword) {
        String xml = jobApiClient.getJobs(keyword).block();

        if (xml == null || xml.isBlank()) {
            throw new ExternalApiException("고용24 API 응답이 비어있습니다.");
        }

        JobDictionaryResponseDto jobDictionaryResponseDto = XmlUtil.fromXml(xml, JobDictionaryResponseDto.class);

        if (jobDictionaryResponseDto.getDJobList() == null || jobDictionaryResponseDto.getDJobList().isEmpty()) {
            throw new ExternalApiException("검색된 직업 정보가 없습니다.");
        }

        return jobDictionaryResponseDto.getDJobList().stream()
                .map(jobItem -> new JobItemResponseDto(jobItem.getCode(), jobItem.getName()))
                .collect(Collectors.toList());
    }
}
