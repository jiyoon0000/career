package com.example.career.global.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JobApiClient {

    private final WebClient webClient;

    @Value("${external.work24-api.service-key}")
    private String authKey;

    public JobApiClient(@Qualifier("jobWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getJobs(String keyword) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("authKey", authKey)
                        .queryParam("returnType", "XML")
                        .queryParam("target", "dJobCD")
                        .queryParam("srchType", "K")
                        .queryParam("keyword", keyword)
                        .queryParam("startPage", 1)
                        .queryParam("display", 50)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);
    }
}
