package com.example.career.domain.studyroom.service;

import com.example.career.domain.studyroom.dto.StudyRoomReservationResponseDto;
import com.example.career.domain.studyroom.dto.StudyRoomReservationRowDto;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomReservationService {

    private final ObjectMapper objectMapper;
    private final StudyRoomRepository studyRoomRepository;

    @Value("${external.seoul-api.base-url}")
    private String baseUrl;

    @Value("${seoul.api.key}")
    private String apiKey;

    @Transactional
    public void saveStudyRoomFromApi() {
        String jsonResponse = fetchStudyRoomFromSeoulApi();

        System.out.println("API 응답: " + jsonResponse);

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode cultureNode = root.get("ListPublicReservationInstitution");

            if (cultureNode == null) {
                throw new RuntimeException("ListPublicReservationInstitution 필드가 없습니다.");
            }

            StudyRoomReservationResponseDto reservationResponseDto = objectMapper.treeToValue(cultureNode, StudyRoomReservationResponseDto.class);

            List<StudyRoomReservationRowDto> rows = reservationResponseDto.getRow();

            if (rows == null || rows.isEmpty()) {
                throw new RuntimeException("청년공간 데이터가 비어 있습니다.");
            }

            List<StudyRoom> studyRooms = rows.stream()
                    .filter(rowDto -> "청년공간".equals(rowDto.getMinClassNm()))
                    .map(this::toStudyRoom)
                    .collect(Collectors.toList());

            studyRoomRepository.saveAll(studyRooms);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("서울시 공공데이터 파싱 실패", e);
        }
    }

    private String fetchStudyRoomFromSeoulApi() {
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("/" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("json", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("ListPublicReservationInstitution", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("1", StandardCharsets.UTF_8));
            urlBuilder.append("/" + URLEncoder.encode("1000", StandardCharsets.UTF_8));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            System.out.println("서울시 OpenAPI 응답 코드: " + responseCode);

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
            e.printStackTrace();
            throw new RuntimeException("서울시 OpenAPI 호출 실패", e);
        }
    }

    private StudyRoom toStudyRoom(StudyRoomReservationRowDto dto) {

        return StudyRoom.builder()
                .source(Source.SERVICE)
                .externalId(dto.getSvcId())
                .name(dto.getSvcNm())
                .description(null)
                .address(dto.getPlaceNm())
                .detailAddress(null)
                .region(dto.getAreaNm())
                .x(parseDoubleOrNull(dto.getX()))
                .y(parseDoubleOrNull(dto.getY()))
                .phone(dto.getTelNo())
                .imageUrl(dto.getImgUrl())
                .category(dto.getMinClassNm())
                .useTime(dto.getVMin() + " ~ " + dto.getVMax())
                .holiday(null)
                .status(dto.getSvcStatNm())
                .payType(dto.getPayAtNm())
                .cancelPolicy(dto.getRevStdDayNm() + " / " + dto.getRevStdDay())
                .detail(dto.getDtlCont())
                .serviceUrl(dto.getSvcUrl())
                .organizationName(null)
                .applyStart(parseDateTime(dto.getRcptBgndt()))
                .applyEnd(parseDateTime(dto.getRcptEnddt()))
                .serviceStart(parseDateTime(dto.getSvcOpnBgndt()))
                .serviceEnd(parseDateTime(dto.getSvcOpnEnddt()))
                .facilityInfo(null)
                .reservationNote(null)
                .approvalMethod(null)
                .build();
    }

    private Double parseDoubleOrNull(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDateTime parseDateTime(String value) {
        try {
            return value != null && !value.isBlank()
                    ? LocalDateTime.parse(value.replace(" ", "T"))
                    : null;
        } catch (Exception e) {
            return null;
        }
    }

}
