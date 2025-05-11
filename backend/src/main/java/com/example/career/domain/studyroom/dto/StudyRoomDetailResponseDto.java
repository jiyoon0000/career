package com.example.career.domain.studyroom.dto;

import com.example.career.domain.studyroom.entity.StudyRoom;
import lombok.Getter;

@Getter
public class StudyRoomDetailResponseDto {

    private final Long id;

    private final String name;

    private final String category;

    private final String region;

    private final String payType;

    private final String imageUrl;

    private final String intro;

    private final String openingHours;

    private final String contact;

    private final String address;

    private final String serviceUrl;

    private final String organizationName;

    private final Double x;
    
    private final Double y;

    public StudyRoomDetailResponseDto(StudyRoom studyRoom) {
        this.id = studyRoom.getId();
        this.name = studyRoom.getName();
        this.category = studyRoom.getCategory();
        this.region = studyRoom.getRegion();
        this.payType = studyRoom.getPayType();
        this.imageUrl = studyRoom.getImageUrl();
        this.intro = studyRoom.getDescription();
        this.openingHours = studyRoom.getUseTime();
        this.contact = studyRoom.getPhone();
        this.address = studyRoom.getAddress();
        this.serviceUrl = studyRoom.getServiceUrl();
        this.organizationName = studyRoom.getOrganizationName();
        this.x = studyRoom.getX();
        this.y = studyRoom.getY();
    }
}
