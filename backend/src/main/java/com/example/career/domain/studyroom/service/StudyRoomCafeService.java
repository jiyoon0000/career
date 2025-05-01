package com.example.career.domain.studyroom.service;

import com.example.career.domain.studyroom.dto.StudyRoomCafeRowDto;
import com.example.career.domain.studyroom.entity.StudyRoom;
import com.example.career.domain.studyroom.enums.Source;
import com.example.career.domain.studyroom.repository.StudyRoomRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomCafeService {

    private final ObjectMapper objectMapper;
    private final StudyRoomRepository studyRoomRepository;

    @Value("${external.seoul-api.base-url}")
    private String baseUrl;

    @Value("${seoul.api.key}")
    private String apiKey;

    @Transactional
    public void saveCafeFromApi() {
        String jsonResponse = fetchCafeFromSeoulApi();

        System.out.println("일자리카페 API 응답: " + jsonResponse);

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode dataNode = root.get("jobCafeOpenInfo");

            if (dataNode == null) {
                throw new RuntimeException("jobCafeOpenInfo 필드가 없습니다.");
            }

            JsonNode rowNode = dataNode.get("row");

            if (rowNode == null || !rowNode.isArray()) {
                throw new RuntimeException("일자리카페 row 필드가 없습니다.");
            }

            List<StudyRoomCafeRowDto> rows = objectMapper.readerForListOf(StudyRoomCafeRowDto.class).readValue(rowNode);

            List<StudyRoom> studyRooms = rows.stream()
                    .map(this::toStudyRoom)
                    .collect(Collectors.toList());

            studyRoomRepository.saveAll(studyRooms);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("일자리카페 데이터 파싱 실패", e);
        }
    }

    private String fetchCafeFromSeoulApi() {
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("/" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("json", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("jobCafeOpenInfo", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("1", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("1000", StandardCharsets.UTF_8));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            System.out.println("서울시 일자리카페 응답 코드: " + responseCode);

            BufferedReader rd;
            if (responseCode >= 200 && responseCode <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("일자리카페 OpenAPI 호출 실패", e);
        }
    }

    private StudyRoom toStudyRoom(StudyRoomCafeRowDto dto) {
        return StudyRoom.builder()
                .source(Source.CAFE)
                .externalId(dto.getCafeNm())
                .name(dto.getCafeNm())
                .description(dto.getSmplIntro())
                .address(dto.getBassAdresCn())
                .detailAddress(dto.getRoadAdres2Cn())
                .region(dto.getGugun())
                .useTime(dto.getUseDt())
                .holiday(dto.getHoliDd())
                .imageUrl(dto.getFileNm())
                .category(dto.getCafeTypeNm())
                .facilityInfo(mergeFacilityInfo(dto))
                .reservationNote(mergeReservationNotes(dto))
                .approvalMethod(dto.getApprMthdNm())
                .build();
    }

    private String mergeFacilityInfo(StudyRoomCafeRowDto dto) {
        return String.join(" / ",
                dto.getFacltInfo1(), dto.getFacltInfo2(), dto.getFacltInfo3(),
                dto.getFacltInfo4(), dto.getFacltInfo5(), dto.getFacltInfo6(),
                dto.getFacltInfo7(), dto.getFacltInfo8(), dto.getFacltInfo9(),
                dto.getFacltInfo10()
        ).replaceAll("null / ", "").replaceAll(" / null", "");
    }

    private String mergeReservationNotes(StudyRoomCafeRowDto dto) {
        return String.join(" / ",
                dto.getRsrvSggst1(), dto.getRsrvSggst2(), dto.getRsrvSggst3(),
                dto.getRsrvSggst4(), dto.getRsrvSggst5(), dto.getRsrvSggst6(),
                dto.getRsrvSggst7(), dto.getRsrvSggst8(), dto.getRsrvSggst9(),
                dto.getRsrvSggst10()
        ).replaceAll("null / ", "").replaceAll(" / null", "");
    }

}
