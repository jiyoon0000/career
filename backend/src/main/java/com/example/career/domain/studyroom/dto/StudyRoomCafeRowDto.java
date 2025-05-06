package com.example.career.domain.studyroom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomCafeRowDto {

    @JsonProperty("CAFE_NM")
    private String cafeNm;

    @JsonProperty("SMPL_INTRO")
    private String smplIntro;

    @JsonProperty("SPACE_INFRO")
    private String spaceInfro;

    @JsonProperty("USE_DT")
    private String useDt;

    @JsonProperty("HOLI_DD")
    private String holiDd;

    @JsonProperty("FACLT_INFO1")
    private String facltInfo1;

    @JsonProperty("FACLT_INFO2")
    private String facltInfo2;

    @JsonProperty("FACLT_INFO3")
    private String facltInfo3;

    @JsonProperty("FACLT_INFO4")
    private String facltInfo4;

    @JsonProperty("FACLT_INFO5")
    private String facltInfo5;

    @JsonProperty("FACLT_INFO6")
    private String facltInfo6;

    @JsonProperty("FACLT_INFO7")
    private String facltInfo7;

    @JsonProperty("FACLT_INFO8")
    private String facltInfo8;

    @JsonProperty("FACLT_INFO9")
    private String facltInfo9;

    @JsonProperty("FACLT_INFO10")
    private String facltInfo10;

    @JsonProperty("RSRV_SGGST1")
    private String rsrvSggst1;

    @JsonProperty("RSRV_SGGST2")
    private String rsrvSggst2;

    @JsonProperty("RSRV_SGGST3")
    private String rsrvSggst3;

    @JsonProperty("RSRV_SGGST4")
    private String rsrvSggst4;

    @JsonProperty("RSRV_SGGST5")
    private String rsrvSggst5;

    @JsonProperty("RSRV_SGGST6")
    private String rsrvSggst6;

    @JsonProperty("RSRV_SGGST7")
    private String rsrvSggst7;

    @JsonProperty("RSRV_SGGST8")
    private String rsrvSggst8;

    @JsonProperty("RSRV_SGGST9")
    private String rsrvSggst9;

    @JsonProperty("RSRV_SGGST10")
    private String rsrvSggst10;

    @JsonProperty("BASS_ADRES_CN")
    private String bassAdresCn;

    @JsonProperty("GUGUN")
    private String gugun;

    @JsonProperty("ROAD_ADRES2_CN")
    private String roadAdres2Cn;

    @JsonProperty("APPR_MTHD_NM")
    private String apprMthdNm;

    @JsonProperty("FILE_NM")
    private String fileNm;

    @JsonProperty("CAFE_TYPE_NM")
    private String cafeTypeNm;
}
