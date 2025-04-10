package com.example.career.domain.onboarding.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JacksonXmlRootElement(localName = "dJobsList")
@Getter
@NoArgsConstructor
public class JobDictionaryResponseDto {

    @JacksonXmlProperty(localName = "total")
    private int total;

    @JacksonXmlProperty(localName = "startPage")
    private int startPage;

    @JacksonXmlProperty(localName = "display")
    private int display;

    @JacksonXmlProperty(localName = "dJobList")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<JobItem> dJobList;

    @Getter
    @NoArgsConstructor
    public static class JobItem {
        @JacksonXmlProperty(localName = "dJobCd")
        private String code;

        @JacksonXmlProperty(localName = "dJobNm")
        private String name;
    }
}
