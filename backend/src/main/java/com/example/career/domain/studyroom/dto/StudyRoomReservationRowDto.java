package com.example.career.domain.studyroom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomReservationRowDto {

    @JsonProperty("GUBUN")
    private String gubun;

    @JsonProperty("SVCID")
    private String svcId;

    @JsonProperty("MAXCLASSNM")
    private String maxClassNm;

    @JsonProperty("MINCLASSNM")
    private String minClassNm;

    @JsonProperty("SVCSTATNM")
    private String svcStatNm;

    @JsonProperty("SVCNM")
    private String svcNm;

    @JsonProperty("PAYATNM")
    private String payAtNm;

    @JsonProperty("PLACENM")
    private String placeNm;

    @JsonProperty("USETGTINFO")
    private String useTgtInfo;

    @JsonProperty("SVCURL")
    private String svcUrl;

    @JsonProperty("X")
    private String x;

    @JsonProperty("Y")
    private String y;

    @JsonProperty("SVCOPNBGNDT")
    private String svcOpnBgndt;

    @JsonProperty("SVCOPNENDDT")
    private String svcOpnEnddt;

    @JsonProperty("RCPTBGNDT")
    private String rcptBgndt;

    @JsonProperty("RCPTENDDT")
    private String rcptEnddt;

    @JsonProperty("AREANM")
    private String areaNm;

    @JsonProperty("IMGURL")
    private String imgUrl;

    @JsonProperty("DTLCONT")
    private String dtlCont;

    @JsonProperty("TELNO")
    private String telNo;

    @JsonProperty("V_MIN")
    private String vMin;

    @JsonProperty("V_MAX")
    private String vMax;

    @JsonProperty("REVSTDDAYNM")
    private String revStdDayNm;

    @JsonProperty("REVSTDDAY")
    private String revStdDay;
}
