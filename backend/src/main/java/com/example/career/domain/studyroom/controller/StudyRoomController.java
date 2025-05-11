package com.example.career.domain.studyroom.controller;

import com.example.career.domain.studyroom.dto.StudyRoomResponseDto;
import com.example.career.domain.studyroom.service.StudyRoomCafeService;
import com.example.career.domain.studyroom.service.StudyRoomQueryService;
import com.example.career.domain.studyroom.service.StudyRoomReservationService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
@Tag(name = "스터디룸", description = "스터디룸 공공데이터 관련 API")
public class StudyRoomController {

    private final StudyRoomReservationService reservationService;
    private final StudyRoomCafeService cafeService;
    private final StudyRoomQueryService studyRoomQueryService;

    @GetMapping("/save")
    @Operation(summary = "스터디룸 데이터 저장", description = "서울시 공공데이터를 수집하여 청년공간/일자리카페 정보 저장")
    public ResponseEntity<CommonResponseDto<Void>> saveStudyRoomsFromApi() {
        reservationService.saveStudyRoomFromApi();
        cafeService.saveCafeFromApi();
        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.CREATE_SUCCESS, null));
    }

    @GetMapping
    @Operation(summary = "스터디룸 전체 목록 조회", description = "청년공간 + 일자리카페 통합 목록 조회")
    public ResponseEntity<CommonResponseDto<List<StudyRoomResponseDto>>> getAllStudyRooms() {
        List<StudyRoomResponseDto> result = studyRoomQueryService.getAllStudyRooms();
        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, result));
    }

    @GetMapping("/filter")
    @Operation(summary = "스터디 룸 필터링", description = "자치구 및 요금 기준으로 스터디룸 필터링")
    public ResponseEntity<CommonResponseDto<List<StudyRoomResponseDto>>> getFilterStudyRooms(@RequestParam(required = false) String region,
                                                                                             @RequestParam(required = false) String payType) {
        List<StudyRoomResponseDto> result = studyRoomQueryService.getFilterStudyRooms(region, payType);
        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, result));
    }

    @GetMapping("/regions")
    @Operation(summary = "스터디룸 자치구 목록 조회", description = "DB에 존재하는 자치구 목록 조회")
    public ResponseEntity<CommonResponseDto<List<String>>> getRegions() {
        List<String> regions = studyRoomQueryService.getAllRegions();
        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.FETCH_SUCCESS, regions));
    }
}
