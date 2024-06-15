package com.gamechanger.service;

import com.gamechanger.client.AiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DesignServiceImpl implements DesignService {

    private final AiClient aiClient;

    public DesignServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @Override
    public String createAIDesign(String prompt) {
        Map<String, String> request = new HashMap<>();
        request.put("text", prompt);
        Map<String, String> response = aiClient.generateImage(request);
        log.info("Task id: {}", response.get("id"));
        return response.get("id");
    }

    @Override
    public byte[] getImage(String id) {
        return aiClient.getImage(id);
    }
}
