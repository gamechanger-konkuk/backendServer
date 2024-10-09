package com.gamechanger.config;

import com.gamechanger.client.LiveblocksClient;
import com.gamechanger.service.LiveblocksService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveblocksConfig {
    private final LiveblocksClient liveblocksClient;

    @Value("${spring.liveBlocks.secretKey}")
    private String secretKey;

    @Autowired
    public LiveblocksConfig(LiveblocksClient liveblocksClient) {
        this.liveblocksClient = liveblocksClient;
    }

    @Bean
    public LiveblocksService liveblocksService() {
        return new LiveblocksService(liveblocksClient);
    }

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
