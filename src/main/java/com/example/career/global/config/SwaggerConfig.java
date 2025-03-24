package com.example.career.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Career API 문서")
                        .description("서울시 공공데이터 창업 경진대회 백엔드 API 명세서")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Kim JiYoon")
                                .email("rlawldbs9128@gmail.com"))
                        .license(new License()
                                .name("Career License")
                                .url("배포 주소 추가 예정")));
    }
}
