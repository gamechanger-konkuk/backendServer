package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.LoginRequest;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    // User
    User join(JoinRequest joinRequest);
    String login(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    boolean checkLoginIdDuplicate(String loginId);
    // Clothes
    Clothes createClothes(String loginId, String clothesName) throws ParseException;
    Clothes getClothes(String loginId, String clothesName);
    List<Clothes> getAllClothes(String loginId);
    Clothes saveClothes(String loginId, String clothesName);
    void deleteClothes(String loginId, String clothesName);
    Clothes changeClothesName(String loginId, String oldClothesName, String newClothesName);
    // Image
    Image uploadUserImage(String loginId, MultipartFile uploadImage, String clothesName) throws IOException;
    Image createAiImageByPrompt(String loginId, String clothesName, String style, String prompt);
    Image removeImageBackground(String loginId, String clothesName, String fileUrl);
}
