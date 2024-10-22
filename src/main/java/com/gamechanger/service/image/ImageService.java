package com.gamechanger.service.image;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;

public interface ImageService {
    // ai
    Image createAiImageByPrompt(Clothes clothes, String style, String prompt);
//    Image createAiImageByImage(String style, String prompt, String imageId, MultipartFile inputImage);
    Image removeImageBackground(Clothes clothes, String fileUrl);
    // s3
    Image uploadImage(byte[] image, String view);
    Image getImage(String fileUrl);
    Image updateImage(Image Image);
    void deleteImage(String fileUrl);
}
