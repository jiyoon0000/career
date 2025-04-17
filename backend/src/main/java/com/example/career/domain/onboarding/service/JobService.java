package com.example.career.domain.onboarding.service;

import com.example.career.domain.onboarding.dto.JobDictionaryResponseDto;
import com.example.career.domain.onboarding.dto.JobItemResponseDto;
import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.repository.JobRepository;
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
    private final JobRepository jobRepository;

    public List<JobItemResponseDto> searchJobs(String keyword) {
        String xml = jobApiClient.getJobs(keyword).block();

        if (xml == null || xml.isBlank()) {
            throw new ExternalApiException("고용24 API 응답이 비어있습니다.");
        }

        JobDictionaryResponseDto jobDictionaryResponseDto = XmlUtil.fromXml(xml, JobDictionaryResponseDto.class);

        List<JobDictionaryResponseDto.JobItem> jobItems = jobDictionaryResponseDto.getDJobList();

        if (jobItems == null || jobItems.isEmpty()) {
            throw new ExternalApiException("검색된 직업 정보가 없습니다.");
        }

        jobItems.forEach(jobItem -> {
            if (!jobRepository.existsByCode(jobItem.getCode())) {
                jobRepository.save(new Job(jobItem.getName(), jobItem.getCode()));
            }
        });

        return jobItems.stream()
                .map(jobItem -> new JobItemResponseDto(jobItem.getCode(), jobItem.getName()))
                .collect(Collectors.toList());
    }
}
