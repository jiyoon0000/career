package com.example.career.domain.onboarding.controller;

import com.example.career.domain.onboarding.dto.CertificateResponseDto;
import com.example.career.domain.onboarding.dto.CertificateSelectionRequestDto;
import com.example.career.domain.onboarding.dto.JobSelectionRequestDto;
import com.example.career.domain.onboarding.dto.JobSelectionResponseDto;
import com.example.career.domain.onboarding.dto.SkillRecommendResponseDto;
import com.example.career.domain.onboarding.dto.SkillSelectionRequestDto;
import com.example.career.domain.onboarding.service.AiService;
import com.example.career.domain.onboarding.service.CertificateFetchService;
import com.example.career.domain.onboarding.service.OnboardingService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.BadRequestException;
import com.example.career.global.security.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
@Tag(name = "Onboarding", description = "온보딩 관련 API")
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final AiService aiService;
    private final CertificateFetchService certificateFetchService;

    @PostMapping("/jobs")
    @Operation(summary = "직무 선택 저장", description = "사용자가 선택한 직무를 저장")
    public ResponseEntity<CommonResponseDto<JobSelectionResponseDto>> saveJobSelection(@AuthenticationPrincipal MemberDetails user,
                                                                                       @RequestBody @Valid JobSelectionRequestDto jobSelectionRequestDto) {
        JobSelectionResponseDto jobSelectionResponseDto = onboardingService.saveJobSelection(user, jobSelectionRequestDto);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.CREATE_SUCCESS, jobSelectionResponseDto));
    }

    @GetMapping("/jobs/selected")
    @Operation(summary = "선택한 직무 조회", description = "회원이 선택한 직무 조회")
    public ResponseEntity<CommonResponseDto<JobSelectionResponseDto>> getSelectedJob(@AuthenticationPrincipal MemberDetails user) {
        JobSelectionResponseDto jobSelectionResponseDto = onboardingService.getSelectedJob(user);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, jobSelectionResponseDto));
    }

    @GetMapping("/skills/recommend")
    @Operation(summary = "추천 스킬 조회", description = "선택한 직무 기반으로 AI가 추천한 20개의 스킬 응답")
    public ResponseEntity<CommonResponseDto<List<SkillRecommendResponseDto>>> recommendSkills(@AuthenticationPrincipal MemberDetails user) {
        List<SkillRecommendResponseDto> skills = aiService.recommendSkills(user);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, skills));
    }

    @PostMapping("/skills")
    @Operation(summary = "선택 스킬 저장", description = "사용자가 선택한 스킬을 저장(최소 1개 ~ 최대 10개")
    public ResponseEntity<CommonResponseDto<String>> saveSkills(@AuthenticationPrincipal MemberDetails user,
                                                                @RequestBody @Valid SkillSelectionRequestDto skillSelectionRequestDto) {
        onboardingService.saveSkillSelection(user, skillSelectionRequestDto);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.CREATE_SUCCESS, null));
    }

    @GetMapping("/certificates/recommend")
    @Operation(summary = "추천 자격증 조회", description = "선택한 직무 기반으로 고용24 API에서 관련 자격증 조회")
    public ResponseEntity<CommonResponseDto<List<CertificateResponseDto>>> recommendCertificates(@AuthenticationPrincipal MemberDetails user) {

        String jobCode = user.getMember().getJob() != null ? user.getMember().getJob().getCode() : null;

        if (jobCode == null) {
            throw new BadRequestException(ErrorCode.JOB_NOT_SELECTED);
        }

        List<CertificateResponseDto> certificates = certificateFetchService.fetchAndSaveCertificates(jobCode);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, certificates));
    }

    @PostMapping("/certificates")
    @Operation(summary = "자격증 선택 저장", description = "사용자가 선택한 자격증을 저장")
    public ResponseEntity<CommonResponseDto<Void>> saveSelectedCertificates(@AuthenticationPrincipal MemberDetails user,
                                                                            @RequestBody @Valid CertificateSelectionRequestDto certificateSelectionRequestDto){

        onboardingService.saveSelectedCertificates(user, certificateSelectionRequestDto.getCertificateNames());

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.CREATE_SUCCESS, null));
    }
}
