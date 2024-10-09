package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Optional;

public interface ClothesService {
    // clothes
    Clothes createClothes(String clothesName) throws ParseException;
    Optional<Clothes> getClothes(String clothesName);
    List<Clothes> getAllClothes();
    Clothes saveClothes(String clothesName);
    void deleteClothes(String clothesName);
    Clothes changeClothesName(String oldClothesName, String newClothesName);
    // image
    Image createAiImageByPrompt(String clothesName, String style, String prompt);
    Image removeImageBackground(String clothesName, String fileUrl);
}
