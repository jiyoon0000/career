package com.example.career.domain.studyroom.service;

import com.example.career.domain.studyroom.dto.StudyRoomResponseDto;
import com.example.career.domain.studyroom.entity.StudyRoom;
import com.example.career.domain.studyroom.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomQueryService {

    private final StudyRoomRepository studyRoomRepository;

    public List<StudyRoomResponseDto> getAllStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomRepository.findAll();

        return studyRooms.stream()
                .map(StudyRoomResponseDto::new)
                .collect(Collectors.toList());
    }

}
