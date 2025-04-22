package com.example.career.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${external.job-api.base-url}")
    private String jobDictionaryBaseUrl;

    @Value("${external.cert-api.base-url}")
    private String certApiBaseUrl;

    @Bean("jobWebClient")
    public WebClient jobWebClient() {
        return WebClient.builder()
                .baseUrl(jobDictionaryBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .build();
    }

    @Bean("certWebClient")
    public WebClient certWebClient() {
        return WebClient.builder()
                .baseUrl(jobDictionaryBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .build();
    }

}
