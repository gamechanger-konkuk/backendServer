package com.gamechanger.controller;

import com.gamechanger.domain.Image;
import com.gamechanger.dto.front.image.CreateImageRequestByPromptDto;
import com.gamechanger.dto.front.image.FileResponseDto;
import com.gamechanger.dto.front.image.RemoveBackgroundRequestDto;
import com.gamechanger.service.ClothesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes/image")
public class ClothesImageController {

    private final ClothesService clothesService;

    @PostMapping("/generate")
    public ResponseEntity<FileResponseDto> createAiImageByPrompt(@Valid @RequestBody CreateImageRequestByPromptDto createImageRequestDto) {
        Image aiImage = clothesService.createAiImageByPrompt(createImageRequestDto.getClothesName(), createImageRequestDto.getDesignStyle(), createImageRequestDto.getPrompt());
        FileResponseDto response = FileResponseDto.builder()
                .imageId(createImageRequestDto.getImageId())
                .roomId(aiImage.getClothes().getRoomId())
                .imageUrl(aiImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/remove-background")
    public ResponseEntity<FileResponseDto> removeImageBackground(@RequestBody RemoveBackgroundRequestDto removeBackgroundRequestDto) {
        Image removedImage = clothesService.removeImageBackground(removeBackgroundRequestDto.getClothesName(), removeBackgroundRequestDto.getImageUrl());
        FileResponseDto response = FileResponseDto.builder()
                .imageId(removeBackgroundRequestDto.getImageId())
                .roomId(removedImage.getClothes().getRoomId())
                .imageUrl(removedImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

//    @PostMapping("/edit/{fileName}")
//    public ResponseEntity<FileResponseDto> saveEditedImage(@RequestPart("imageFile") MultipartFile multipartFile, @PathVariable String fileName) {
//        imageService.deleteImage(fileName);
//        Image editedImage = imageService.uploadExistingImage(multipartFile, "front");
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(new FileResponseDto(editedImage.getFileName(), editedImage.getFileUrl()));
//    }
}
