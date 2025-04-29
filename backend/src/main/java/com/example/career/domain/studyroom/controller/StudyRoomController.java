package com.example.career.domain.studyroom.controller;

import com.example.career.domain.studyroom.service.StudyRoomService;
import com.example.career.global.common.CommonResponseDto;
import com.example.career.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-rooms")
@Tag(name = "스터디룸", description = "스터디룸 공공데이터 관련 API")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @GetMapping("/save")
    @Operation(summary = "스터디룸 데이터 저장", description = "서울시 공공데이터를 수집하여 스터디룸 정보 저장")
    public ResponseEntity<CommonResponseDto<Void>> saveStudyRoomsFromApi() {
        studyRoomService.saveStudyRoomFromApi();
        return ResponseEntity.ok(CommonResponseDto.success(SuccessCode.CREATE_SUCCESS, null));
    }
}
