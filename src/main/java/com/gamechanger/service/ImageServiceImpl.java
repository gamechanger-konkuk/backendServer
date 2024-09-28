package com.gamechanger.service;

import com.gamechanger.client.AiClient;
import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.dto.ai.image.CreateImageRequestByPromptToAi;
import com.gamechanger.repository.ImageRepository;
import com.gamechanger.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AiClient aiClient;
    private final FileService fileService;
    private final ImageRepository imageRepository;

    @Override
    public Image createAiImageByPrompt(Clothes clothes, String style, String prompt) {
        CreateImageRequestByPromptToAi request = CreateImageRequestByPromptToAi.builder()
                .style(style)
                .text_prompt(prompt)
                .build();
        byte[] responseImage = aiClient.generateImageByPrompt(request);
        Image uploadImage = this.uploadImage(responseImage, "front");
        uploadImage.setClothes(clothes);
        return imageRepository.save(uploadImage);
    }

    @Override
    public Image removeImageBackground(String fileUrl) {
        byte[] downloadFile = fileService.downloadFile(fileUrl);
        byte[] responseImage = aiClient.removeImageBackground(downloadFile);
        Image uploadImage = this.uploadImage(responseImage, "front");
        return imageRepository.save(uploadImage);
    }

    @Override
    public Image uploadImage(byte[] image, String view) {
        String fileUrl = fileService.uploadFile(image);
        return imageRepository.save(Image.builder()
                .fileName(FileUtils.getFileNameFromUrl(fileUrl))
                .fileUrl(fileUrl)
                .view(view)
                .locationX(0.0f)    // 기본값. 중앙으로 설정하기. 프론트에서 전달해야 한다면 인자로 전달받기
                .locationY(0.0f)
                .build());
    }

    @Override
    public Image getImage(String fileUrl) {
        return imageRepository.findByFileName(fileUrl);
    }


    @Override
    public Image updateImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(String fileName) {
        fileService.deleteFile(fileName);
        imageRepository.deleteById(fileName);
    }

}
