package com.example.career.global.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CertApiClient {

    @Qualifier("certWebClient")
    private final WebClient webClient;

    @Value("${external.work24-api.service-key}")
    private String authKey;

    public Mono<String> getCertificatesByJobCode(String jobCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("authKey", authKey)
                        .queryParam("returnType", "XML")
                        .queryParam("target", "JOBDTL")
                        .queryParam("jobGb", "1")
                        .queryParam("jobCd", jobCode)
                        .queryParam("dtlGb", "3")
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);
    }
}
