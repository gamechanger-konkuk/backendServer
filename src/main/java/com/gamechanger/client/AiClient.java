package com.gamechanger.client;

import com.gamechanger.dto.ai.image.CreateImageRequestByPromptToAi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "aiClient", url = "${ai.server.url}")
public interface AiClient {

    @PostMapping("/generate-image")
    byte[] generateImageByPrompt(@RequestBody CreateImageRequestByPromptToAi createImageRequestByPromptToAi);

//    @PostMapping("/submit-image-prompt")
//    byte[] generateImageByImage(@RequestBody CreateImageRequestByImageDto createImageRequestByImageDto);

    @PostMapping(value = "/remove-background", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    byte[] removeImageBackground(@RequestPart("file") byte[] image);

}

