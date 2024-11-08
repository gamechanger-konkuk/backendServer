package com.gamechanger.service.image;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;

import java.util.List;

public interface ImageService {
    // ai
    Image createAiImageByPrompt(Clothes clothes, String style, String prompt);
    Image removeImageBackground(Clothes clothes, String fileUrl);
    List<String> recommendPrompt(String prompt, int recommend_size);
    // s3
    Image uploadImage(Clothes clothes, byte[] image, String view);
    Image getImage(String fileUrl);
    Image updateImage(Image Image);
    void deleteImage(String fileName);
    void deleteAllImage(Clothes clothes);
}
