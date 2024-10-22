package com.gamechanger.service.clothes;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.domain.User;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ClothesService {
    // Clothes
    Clothes createClothes(User user, String clothesName) throws ParseException;
    Optional<Clothes> getClothes(Long systemClothesId);
    List<Clothes> getAllClothes();
    Clothes saveClothes(Long systemClothesId);
    void deleteClothes(Long systemClothesId);
    Clothes changeClothesName(Long systemClothesId, String newClothesName);
    // Image
    Image uploadUserImage(Long systemClothesId, MultipartFile uploadImage, String clothesName) throws IOException;
    Image createAiImageByPrompt(Long systemClothesId, String clothesName, String style, String prompt);
    Image removeImageBackground(Long systemClothesId, String clothesName, String fileUrl);
}
