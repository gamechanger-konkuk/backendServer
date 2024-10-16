package com.gamechanger.controller;

import com.gamechanger.domain.Image;
import com.gamechanger.dto.front.image.CreateImageRequestByPrompt;
import com.gamechanger.dto.front.image.FileResponse;
import com.gamechanger.dto.front.image.RemoveBackgroundRequest;
import com.gamechanger.dto.front.image.UploadUserImageRequest;
import com.gamechanger.service.ClothesService;
import com.gamechanger.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.gamechanger.util.jwt.JwtUtils.getCurrentLoginId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadUserImage(@Valid @RequestBody UploadUserImageRequest uploadUserImageRequest) throws IOException {
        String loginId = getCurrentLoginId();
        Image uploadedImage = userService.uploadUserImage(loginId, uploadUserImageRequest.getUploadImage(), uploadUserImageRequest.getClothesName());
        log.info("사용자 {}의 티셔츠 {}에 이미지 {}가 추가되었습니다.", loginId, uploadUserImageRequest.getClothesName(), uploadedImage.getFileName());
        FileResponse response = FileResponse.builder()
                .imageId(uploadUserImageRequest.getImageId())
                .roomId(uploadedImage.getClothes().getRoomId())
                .imageUrl(uploadedImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(response);
    }

    @PostMapping("/generate")
    public ResponseEntity<FileResponse> createAiImageByPrompt(@Valid @RequestBody CreateImageRequestByPrompt createImageRequest) {
        String loginId = getCurrentLoginId();
        Image aiImage = userService.createAiImageByPrompt(loginId, createImageRequest.getClothesName(), createImageRequest.getDesignStyle(), createImageRequest.getPrompt());
        log.info("사용자 {}의 티셔츠 {}에 이미지 {}가 생성되었습니다.", loginId, createImageRequest.getClothesName(), aiImage.getFileName());
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
        String loginId = getCurrentLoginId();
        Image removedImage = userService.removeImageBackground(loginId, removeBackgroundRequest.getClothesName(), removeBackgroundRequest.getImageUrl());
        log.info("사용자 {}의 티셔츠 {}의 이미지 {} 배경이 제거되었습니다.", loginId, removeBackgroundRequest.getClothesName(), removedImage.getFileName());
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
