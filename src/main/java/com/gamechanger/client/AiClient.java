package com.gamechanger.client;

import com.gamechanger.dto.ai.image.CreateImageRequestByPromptToAi;
import com.gamechanger.dto.ai.image.RecommendPromptRequestToAi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "aiClient", url = "${ai.server.url}")
public interface AiClient {

    @PostMapping("/generate-image")
    byte[] generateImageByPrompt(@RequestBody CreateImageRequestByPromptToAi createImageRequestByPromptToAi);

    @PostMapping(value = "/remove-background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    byte[] removeImageBackground(@RequestPart("file") MultipartFile image);

    @PostMapping(value = "/prompt-recommend")
    List<String> recommendPrompt(@RequestBody RecommendPromptRequestToAi promptRecommendRequest);
}

