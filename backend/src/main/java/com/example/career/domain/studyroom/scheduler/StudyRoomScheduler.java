package com.example.career.domain.studyroom.scheduler;

import com.example.career.domain.studyroom.repository.StudyRoomRepository;
import com.example.career.domain.studyroom.service.StudyRoomCafeService;
import com.example.career.domain.studyroom.service.StudyRoomReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomScheduler {

    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomReservationService studyRoomReservationService;
    private final StudyRoomCafeService studyRoomCafeService;

    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void refreshStudyRoomData() {
        log.info("[스터디룸 스케줄러] 기존 study_room 데이터 전체 삭제 시작");
        studyRoomRepository.deleteAll();

        log.info("[스터디룸 스케줄러] 청년공간 데이터 저장 시작");
        studyRoomReservationService.saveStudyRoomFromApi();

        log.info("[스터디룸 스케줄러] 일자리카페 데이터 저장 시작");
        studyRoomCafeService.saveCafeFromApi();

        log.info("[스터디룸 스케줄러] study_room 전체 데이터 재등록 완료");
    }
}
