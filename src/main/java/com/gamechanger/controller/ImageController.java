package com.gamechanger.controller;

import com.gamechanger.domain.Image;
import com.gamechanger.dto.front.image.*;
import com.gamechanger.service.image.ImageService;
import com.gamechanger.service.user.UserService;
import com.gamechanger.util.FileUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.gamechanger.util.jwt.JwtUtils.getCurrentLoginId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final UserService userService;
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadUserImage(@RequestParam("clothesName") String clothesName, @RequestParam("imageId") String imageId, @RequestParam("uploadImage") MultipartFile uploadImage) throws IOException {
        String loginId = getCurrentLoginId();
        log.info("사용자 {}의 티셔츠 {}에 이미지를 업로드합니다.", loginId, clothesName);
        Image uploadedImage = userService.uploadUserImage(loginId, uploadImage, clothesName);
        log.info("사용자 {}의 티셔츠 {}에 이미지 {}가 업로드되었습니다.", loginId, clothesName, uploadedImage.getFileName());
        FileResponse response = FileResponse.builder()
                .imageId(imageId)
                .roomId(uploadedImage.getClothes().getRoomId())
                .imageUrl(uploadedImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/generate")
    public ResponseEntity<FileResponse> createAiImageByPrompt(@Valid @RequestBody CreateImageRequestByPrompt createImageRequest) {
        String loginId = getCurrentLoginId();
        String clothesName = createImageRequest.getClothesName();
        String designStyle = createImageRequest.getDesignStyle();
        String prompt = createImageRequest.getPrompt();
        log.info("사용자 {}의 티셔츠 {}에 스타일 {}, 프롬프트 {}로 이미지를 생성합니다.", loginId, clothesName, designStyle, prompt);
        Image aiImage = userService.createAiImageByPrompt(loginId, clothesName, designStyle, prompt);
        log.info("사용자 {}의 티셔츠 {}에 이미지 {}가 생성되었습니다.", loginId, clothesName, aiImage.getFileName());
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
        String clothesName = removeBackgroundRequest.getClothesName();
        String imageUrl = removeBackgroundRequest.getImageUrl();
        log.info("사용자 {}의 티셔츠 {}의 이미지 {}의 배경을 제거합니다.", loginId, clothesName, FileUtils.getFileNameFromUrl(imageUrl));
        Image removedImage = userService.removeImageBackground(loginId, clothesName, imageUrl);
        log.info("사용자 {}의 티셔츠 {}의 이미지 {}의 배경이 제거되었습니다.", loginId, clothesName, removedImage.getFileName());
        FileResponse response = FileResponse.builder()
                .imageId(removeBackgroundRequest.getImageId())
                .roomId(removedImage.getClothes().getRoomId())
                .imageUrl(removedImage.getFileUrl())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/recommend-prompt")
    public ResponseEntity<List<String>> recommendPrompt(@RequestBody RecommendPromptRequest request) {
        String user_prompt = request.getPrompt();
        log.info("입력한 프롬프트 {}에 적합한 프롬프트를 추천합니다.", user_prompt);
        List<String> prompt_list = imageService.recommendPrompt(user_prompt, 10);
        log.info("입력한 프롬프트 {}에 적합한 프롬프트 {}개가 추천되었습니다.", user_prompt, prompt_list.size());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(prompt_list);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImage(@RequestBody DeleteImageRequest deleteImageRequest) {
        String fileName = FileUtils.getFileNameFromUrl(deleteImageRequest.getFileUrl());
        log.info("이미지 {}을 삭제합니다.", fileName);
        imageService.deleteImage(fileName);
        log.info("이미지 {}이 삭제되었습니다.", fileName);
        return ResponseEntity.noContent().build();
    }
}
