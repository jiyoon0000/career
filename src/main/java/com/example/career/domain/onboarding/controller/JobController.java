package com.example.career.domain.onboarding.controller;

import com.example.career.domain.onboarding.dto.JobItemResponseDto;
import com.example.career.domain.onboarding.service.JobService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import com.example.career.global.security.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/onboarding")
@Tag(name = "Onboarding", description = "온보딩 관련 API")
public class JobController {

    private final JobService jobService;

    @GetMapping("jobs/search")
    @Operation(summary = "직업 검색", description = "고용24 API를 통해 직업명 검색")
    public ResponseEntity<CommonResponseDto<List<JobItemResponseDto>>> searchJobs(@RequestParam String keyword,
                                                                                  @AuthenticationPrincipal MemberDetails member) {
        List<JobItemResponseDto> jobs = jobService.searchJobs(keyword);

        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, jobs));
    }
}
