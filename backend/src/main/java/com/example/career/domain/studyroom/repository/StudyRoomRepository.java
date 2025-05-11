package com.example.career.domain.studyroom.repository;

import com.example.career.domain.studyroom.entity.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long>, JpaSpecificationExecutor<StudyRoom> {

    @Query("SELECT DISTINCT s.region FROM StudyRoom s WHERE s.region IS NOT NULL")
    List<String> findDistinctRegion();
}
