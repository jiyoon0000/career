package com.example.career.domain.onboarding.service;

import com.example.career.domain.member.entity.Member;
import com.example.career.domain.member.repository.MemberRepository;
import com.example.career.domain.onboarding.dto.JobSelectionRequestDto;
import com.example.career.domain.onboarding.dto.JobSelectionResponseDto;
import com.example.career.domain.onboarding.dto.SkillSelectionRequestDto;
import com.example.career.domain.onboarding.entity.Certificate;
import com.example.career.domain.onboarding.entity.Job;
import com.example.career.domain.onboarding.entity.MemberCertificate;
import com.example.career.domain.onboarding.entity.MemberSkill;
import com.example.career.domain.onboarding.repository.CertificateRepository;
import com.example.career.domain.onboarding.repository.JobRepository;
import com.example.career.domain.onboarding.repository.MemberCertificateRepository;
import com.example.career.domain.onboarding.repository.MemberSkillRepository;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.error.exception.NotFoundException;
import com.example.career.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;
    private final MemberSkillRepository memberSkillRepository;
    private final CertificateRepository certificateRepository;
    private final MemberCertificateRepository memberCertificateRepository;

    @Transactional
    public JobSelectionResponseDto saveJobSelection(MemberDetails user, JobSelectionRequestDto jobSelectionRequestDto) {

        Member member = memberRepository.findById(user.getMember().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Job job = jobRepository.findByCode(jobSelectionRequestDto.getJobCode())
                .orElseThrow(() -> new BadRequestException(ErrorCode.JOB_NOT_FOUND));

        member.selectJob(job);

        return new JobSelectionResponseDto(job.getCode(), job.getName());
    }

    public JobSelectionResponseDto getSelectedJob(MemberDetails user) {
        Member member = memberRepository.findById(user.getMember().getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Job job = member.getJob();

        if (job == null) {
            throw new BadRequestException(ErrorCode.JOB_NOT_SELECTED);
        }

        return new JobSelectionResponseDto(job.getCode(), job.getName());
    }

    @Transactional
    public void saveSkillSelection(MemberDetails user, SkillSelectionRequestDto skillSelectionRequestDto) {
        Long memberId = user.getMember().getId();

        if (skillSelectionRequestDto.getSkills().isEmpty() || skillSelectionRequestDto.getSkills().size() > 10) {
            throw new BadRequestException(ErrorCode.INVALID_SKILL_COUNT);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        memberSkillRepository.deleteAllByMemberId(memberId);

        List<MemberSkill> skills = skillSelectionRequestDto.getSkills().stream()
                .map(skillName -> new MemberSkill(member, skillName))
                .collect(Collectors.toList());

        memberSkillRepository.saveAll(skills);
    }

    @Transactional
    public void saveSelectedCertificates(MemberDetails user, List<String> certificateNames) {
        Long memberId = user.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        List<Certificate> certificates = certificateRepository.findAllByNameIn(certificateNames);

        for (Certificate certificate : certificates) {
            boolean alreadyExists = memberCertificateRepository.existsByMemberAndCertificate(member, certificate);

            if (!alreadyExists) {
                memberCertificateRepository.save(new MemberCertificate(member, certificate));
            }
        }
    }
}
