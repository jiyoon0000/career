package com.example.career.domain.studyroom.entity;

import com.example.career.domain.studyroom.enums.Source;
import com.example.career.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Source source;

    @Column(length = 100, unique = true)
    private String externalId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String region;

    private Double x;

    private Double y;

    private String phone;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String useTime;
}
