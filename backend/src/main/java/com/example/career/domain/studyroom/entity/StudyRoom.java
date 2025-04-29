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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "study_room")
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

    @Builder
    public StudyRoom(Source source, String externalId, String name, String address, String region,
                     Double x, Double y, String phone, String category, String imageUrl, String useTime) {
        this.source = source;
        this.externalId = externalId;
        this.name = name;
        this.address = address;
        this.region = region;
        this.x = x;
        this.y = y;
        this.phone = phone;
        this.category = category;
        this.imageUrl = imageUrl;
        this.useTime = useTime;
    }
}
