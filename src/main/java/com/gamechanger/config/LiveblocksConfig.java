package com.gamechanger.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveblocksConfig {
    @Value("${spring.liveBlocks.secretKey}")
    private String secretKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // Authorization 헤더에 Bearer 토큰 추가
                requestTemplate.header("Authorization", "Bearer " + secretKey);
            }
        };
    }
}
