package com.gamechanger.service.image;

import com.gamechanger.client.AiClient;
import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.dto.ai.image.CreateImageRequestByPromptToAi;
import com.gamechanger.dto.ai.image.RecommendPromptRequestToAi;
import com.gamechanger.repository.ImageRepository;
import com.gamechanger.util.CustomMultipartFile;
import com.gamechanger.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final AiClient aiClient;

    @Override
    public Image createAiImageByPrompt(Clothes clothes, String style, String prompt) {
        CreateImageRequestByPromptToAi request = CreateImageRequestByPromptToAi.builder()
                .style(style)
                .text_prompt(prompt)
                .build();
        byte[] responseImage = aiClient.generateImageByPrompt(request);
        Image uploadImage = this.uploadImage(clothes, responseImage, "front");
        uploadImage.setClothes(clothes);
        return imageRepository.save(uploadImage);
    }

    @Override
    public Image removeImageBackground(Clothes clothes, String fileUrl) {
        String fileName = FileUtils.getFileNameFromUrl(fileUrl);
        byte[] downloadFile = fileService.downloadFile(fileName);
        this.deleteImage(fileName);
        MultipartFile multipartFile = new CustomMultipartFile(downloadFile, fileName);
        byte[] responseImage = aiClient.removeImageBackground(multipartFile);
        Image uploadImage = this.uploadImage(clothes, responseImage, "front");
        uploadImage.setClothes(clothes);
        return imageRepository.save(uploadImage);
    }

    @Override
    public List<String> recommendPrompt(String prompt, int recommend_size) {
        RecommendPromptRequestToAi request = RecommendPromptRequestToAi.builder()
                .user_prompt(prompt)
                .recommend_size(recommend_size)
                .build();
        return aiClient.recommendPrompt(request);
    }

    @Override
    public Image uploadImage(Clothes clothes, byte[] image, String view) {
        String fileUrl = fileService.uploadFile(image);
        return imageRepository.save(Image.builder()
                .fileName(FileUtils.getFileNameFromUrl(fileUrl))
                .fileUrl(fileUrl)
                .clothes(clothes)
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
        Image image = imageRepository.findByFileName(fileName);
        image.removeImageFromClothes();
        imageRepository.deleteByFileName(fileName);
    }

    @Override
    public void deleteAllImage(Clothes clothes) {
        Long clothesId = clothes.getSystemClothesId();
        List<Image> deletingImages = imageRepository.findImagesBySystemClothesId(clothesId);
        for (Image image : deletingImages) {
            fileService.deleteFile(image.getFileName());
        }
        imageRepository.deleteImagesBySystemClothesId(clothesId);
    }
}
