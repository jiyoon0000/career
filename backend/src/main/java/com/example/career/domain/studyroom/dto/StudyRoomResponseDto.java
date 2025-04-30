package com.example.career.domain.studyroom.dto;

import com.example.career.domain.studyroom.entity.StudyRoom;
import com.example.career.domain.studyroom.enums.Source;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyRoomResponseDto {

    private final Long id;
    private final Source source;
    private final String externalId;
    private final String name;
    private final String description;
    private final String address;
    private final String detailAddress;
    private final String region;
    private final Double x;
    private final Double y;
    private final String phone;
    private final String imageUrl;
    private final String category;
    private final String useTime;
    private final String holiday;
    private final String status;
    private final String payType;
    private final String cancelPolicy;
    private final String detail;
    private final String serviceUrl;
    private final String organizationName;
    private final LocalDateTime applyStart;
    private final LocalDateTime applyEnd;
    private final LocalDateTime serviceStart;
    private final LocalDateTime serviceEnd;
    private final String facilityInfo;
    private final String reservationNote;
    private final String approvalMethod;

    public StudyRoomResponseDto(StudyRoom entity) {
        this.id = entity.getId();
        this.source = entity.getSource();
        this.externalId = entity.getExternalId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.address = entity.getAddress();
        this.detailAddress = entity.getDetailAddress();
        this.region = entity.getRegion();
        this.x = entity.getX();
        this.y = entity.getY();
        this.phone = entity.getPhone();
        this.imageUrl = entity.getImageUrl();
        this.category = entity.getCategory();
        this.useTime = entity.getUseTime();
        this.holiday = entity.getHoliday();
        this.status = entity.getStatus();
        this.payType = entity.getPayType();
        this.cancelPolicy = entity.getCancelPolicy();
        this.detail = entity.getDetail();
        this.serviceUrl = entity.getServiceUrl();
        this.organizationName = entity.getOrganizationName();
        this.applyStart = entity.getApplyStart();
        this.applyEnd = entity.getApplyEnd();
        this.serviceStart = entity.getServiceStart();
        this.serviceEnd = entity.getServiceEnd();
        this.facilityInfo = entity.getFacilityInfo();
        this.reservationNote = entity.getReservationNote();
        this.approvalMethod = entity.getApprovalMethod();
    }
}
