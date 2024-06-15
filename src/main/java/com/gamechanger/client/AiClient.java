package com.gamechanger.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "aiClient", url = "${ai.server.url}")
public interface AiClient {
    @PostMapping("/submit-text")
    Map<String, String> generateImage(@RequestBody Map<String, String> prompt);

    @GetMapping("/get-image/{id}")
    byte[] getImage(@PathVariable("id") String id);
}

