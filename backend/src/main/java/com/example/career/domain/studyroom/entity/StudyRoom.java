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

    private String gubun;

    @Column(length = 100, unique = true)
    private String externalId;

    @Column(name = "max_class_nm")
    private String maxClassNm;

    @Column(name = "min_class_nm")
    private String minClassNm;

    @Column(name = "svc_stat_nm")
    private String svcStatNm;

    @Column(nullable = false)
    private String name;

    @Column(name = "pay_at_nm")
    private String payAtNm;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "use_tgt_info", columnDefinition = "TEXT")
    private String useTgtInfo;

    @Column(name = "svc_url", columnDefinition = "TEXT")
    private String svcUrl;

    private Double x;

    private Double y;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "apply_start_date")
    private LocalDateTime applyStartDate;

    @Column(name = "apply_end_date")
    private LocalDateTime applyEndDate;

    private String region;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "detail_content", columnDefinition = "TEXT")
    private String detailContent;

    private String phone;

    @Column(name = "v_min")
    private String vMin;

    @Column(name = "v_max")
    private String vMax;

    @Column(name = "rev_std_day_nm")
    private String revStdDayNm;

    @Column(name = "rev_std_day")
    private String revStdDay;

    private String useTime;

    private String category;

    @Builder
    public StudyRoom(Source source, String gubun, String externalId, String maxClassNm, String minClassNm,
                     String svcStatNm, String name, String payAtNm, String address, String useTgtInfo,
                     String svcUrl, Double x, Double y, LocalDateTime startDate, LocalDateTime endDate,
                     LocalDateTime applyStartDate, LocalDateTime applyEndDate, String region, String imageUrl, String detailContent,
                     String phone, String vMin, String vMax, String revStdDayNm, String revStdDay,
                     String useTime, String category) {
        this.source = source;
        this.gubun = gubun;
        this.externalId = externalId;
        this.maxClassNm = maxClassNm;
        this.minClassNm = minClassNm;
        this.svcStatNm = svcStatNm;
        this.name = name;
        this.payAtNm = payAtNm;
        this.address = address;
        this.useTgtInfo = useTgtInfo;
        this.svcUrl = svcUrl;
        this.x = x;
        this.y = y;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.region = region;
        this.imageUrl = imageUrl;
        this.detailContent = detailContent;
        this.phone = phone;
        this.vMin = vMin;
        this.vMax = vMax;
        this.revStdDayNm = revStdDayNm;
        this.revStdDay = revStdDay;
        this.useTime = useTime;
        this.category = category;
    }
    
}
