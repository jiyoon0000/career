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

import java.time.LocalDateTime;

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

    @Column(name = "external_id", length = 100, unique = true)
    private String externalId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "detail_address", columnDefinition = "TEXT")
    private String detailAddress;

    @Column(length = 50)
    private String region;

    private Double x;
    private Double y;

    private String phone;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    private String category;

    @Column(name = "use_time", length = 100)
    private String useTime;

    @Column(columnDefinition = "TEXT")
    private String holiday;

    @Column(length = 50)
    private String status;

    @Column(name = "pay_type", length = 50)
    private String payType;

    @Column(name = "cancel_policy", columnDefinition = "TEXT")
    private String cancelPolicy;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "service_url", columnDefinition = "TEXT")
    private String serviceUrl;

    @Column(name = "organization_name", length = 100)
    private String organizationName;

    @Column(name = "apply_start")
    private LocalDateTime applyStart;

    @Column(name = "apply_end")
    private LocalDateTime applyEnd;

    @Column(name = "service_start")
    private LocalDateTime serviceStart;

    @Column(name = "service_end")
    private LocalDateTime serviceEnd;

    @Column(name = "facility_info", columnDefinition = "TEXT")
    private String facilityInfo;

    @Column(name = "reservation_note", columnDefinition = "TEXT")
    private String reservationNote;

    @Column(name = "approval_method", length = 50)
    private String approvalMethod;

    @Builder
    public StudyRoom(Source source, String externalId, String name, String description,
                     String address, String detailAddress, String region,
                     Double x, Double y, String phone, String imageUrl, String category, String useTime, String holiday,
                     String status, String payType, String cancelPolicy, String detail, String serviceUrl,
                     String organizationName, LocalDateTime applyStart, LocalDateTime applyEnd,
                     LocalDateTime serviceStart, LocalDateTime serviceEnd,
                     String facilityInfo, String reservationNote, String approvalMethod) {
        this.source = source;
        this.externalId = externalId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.detailAddress = detailAddress;
        this.region = region;
        this.x = x;
        this.y = y;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.category = category;
        this.useTime = useTime;
        this.holiday = holiday;
        this.status = status;
        this.payType = payType;
        this.cancelPolicy = cancelPolicy;
        this.detail = detail;
        this.serviceUrl = serviceUrl;
        this.organizationName = organizationName;
        this.applyStart = applyStart;
        this.applyEnd = applyEnd;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.facilityInfo = facilityInfo;
        this.reservationNote = reservationNote;
        this.approvalMethod = approvalMethod;
    }

}
