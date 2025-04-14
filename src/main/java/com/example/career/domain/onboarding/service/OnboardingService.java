package com.example.career.domain.onboarding.service;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.domain.onboarding.dto.JobSelectionRequestDto;
import com.example.career.domain.onboarding.dto.JobSelectionResponseDto;
import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.repository.JobRepository;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;

    @Transactional
    public JobSelectionResponseDto saveJobSelection(MemberDetails user, JobSelectionRequestDto jobSelectionRequestDto) {
        Long memberId = user.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));

        Job job = jobRepository.findByCode(jobSelectionRequestDto.getJobCode())
                .orElseThrow(() -> new BadRequestException(ErrorCode.JOB_NOT_FOUND));

        member.selectJob(job);

        return new JobSelectionResponseDto(job.getCode(), job.getName());
    }
}
