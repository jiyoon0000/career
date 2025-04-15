package com.example.career.domain.onboarding.controller;

import com.example.career.domain.onboarding.dto.JobSelectionRequestDto;
import com.example.career.domain.onboarding.dto.JobSelectionResponseDto;
import com.example.career.domain.onboarding.service.OnboardingService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
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

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
@Tag(name = "Onboarding", description = "온보딩 관련 API")
public class OnboardingController {

    private final OnboardingService onboardingService;

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
}
