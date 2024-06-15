package com.gamechanger.controller;

import com.gamechanger.dto.PromptDto;
import com.gamechanger.service.DesignService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class DesignController {

    private final DesignService designService;

    public DesignController(DesignService designService) {
        this.designService = designService;
    }

    @PostMapping("/start-image-generation")
    public ResponseEntity<String> createAIDesign(@RequestBody @Valid PromptDto promptDto) {
        String id = designService.createAIDesign(promptDto.getPrompt());
        log.info("Input prompt: {}, Task id: {}", promptDto.getPrompt(), id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/get-image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        byte[] image = designService.getImage(id);
        log.info("Output image_byte: {}", image);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

}
