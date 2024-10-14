package com.gamechanger.dto.front.image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class UploadUserImageRequest {
    private String clothesName;
    private String imageId;
    private MultipartFile uploadImage;
}
