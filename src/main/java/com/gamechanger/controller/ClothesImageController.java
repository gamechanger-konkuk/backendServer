package com.gamechanger.controller;

import com.gamechanger.domain.Image;
import com.gamechanger.dto.front.image.CreateImageRequestByPrompt;
import com.gamechanger.dto.front.image.FileResponse;
import com.gamechanger.dto.front.image.RemoveBackgroundRequest;
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
    public ResponseEntity<FileResponse> createAiImageByPrompt(@Valid @RequestBody CreateImageRequestByPrompt createImageRequest) {
        Image aiImage = clothesService.createAiImageByPrompt(createImageRequest.getClothesName(), createImageRequest.getDesignStyle(), createImageRequest.getPrompt());
        FileResponse response = FileResponse.builder()
                .imageId(createImageRequest.getImageId())
                .roomId(aiImage.getClothes().getRoomId())
                .imageUrl(aiImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/remove-background")
    public ResponseEntity<FileResponse> removeImageBackground(@RequestBody RemoveBackgroundRequest removeBackgroundRequest) {
        Image removedImage = clothesService.removeImageBackground(removeBackgroundRequest.getClothesName(), removeBackgroundRequest.getImageUrl());
        FileResponse response = FileResponse.builder()
                .imageId(removeBackgroundRequest.getImageId())
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
