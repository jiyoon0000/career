package com.example.career.domain.studyroom.service;

import com.example.career.domain.studyroom.dto.StudyRoomResponseDto;
import com.example.career.domain.studyroom.entity.StudyRoom;
import com.example.career.domain.studyroom.repository.StudyRoomRepository;
import com.example.career.global.error.errorcode.ErrorCode;
import com.example.career.global.error.exception.NotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomQueryService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoomResponseDto> getAllStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomRepository.findAll();

        return studyRooms.stream()
                .map(StudyRoomResponseDto::new)
                .toList();
    }

    public List<StudyRoomResponseDto> getFilterStudyRooms(String region, String payType) {
        List<StudyRoom> filtered = studyRoomRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (region != null && !region.isBlank()) {
                predicates.add(cb.equal(root.get("region"), region));
            }

            if (payType != null && !payType.isBlank()) {
                predicates.add(cb.equal(root.get("payType"), payType));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return filtered.stream()
                .map(StudyRoomResponseDto::new)
                .toList();
    }

    public List<String> getAllRegions() {
        return studyRoomRepository.findDistinctRegion();
    }

    public StudyRoom findById(Long id) {
        return studyRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STUDY_ROOM_NOT_FOUND));
    }

}
