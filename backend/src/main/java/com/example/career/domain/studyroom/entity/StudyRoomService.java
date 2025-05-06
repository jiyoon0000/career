package com.example.career.domain.studyroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRoomService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    @Column(nullable = false)
    private String serviceId;

    @Column(columnDefinition = "TEXT")
    private String feeUrl;

    private LocalDateTime serviceBegin;

    private LocalDateTime serviceEnd;

    private LocalDateTime receptionBegin;

    private LocalDateTime receptionEnd;

    private Integer recruitNumber;

    private String payType;

    private String contactTel;

    private String organizationName;

    @Column(columnDefinition = "TEXT")
    private String cancelPolicy;

    @Column(columnDefinition = "TEXT")
    private String detail;
}
