package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.LoginRequest;
import com.gamechanger.repository.UserRepository;
import com.gamechanger.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ClothesService clothesService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // User
    @Override
    public User join(JoinRequest joinRequest) {
        // 중복 id 존재 시 null 반환
        if (checkLoginIdDuplicate(joinRequest.getLoginId())) {
            return null;
        }
        return userRepository.save(joinRequest.toEntity(encoder.encode(joinRequest.getPassword())));
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginRequest.getLoginId());
        // id에 해당하는 유저가 없으면 null 반환
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        // 비밀번호가 틀리면 null 반환
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        return jwtTokenProvider.createToken(user.getLoginId(), user.getUserName());
    }

    @Override
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElse(null);
    }

    @Override
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    //Clothes
    @Override
    public Clothes createClothes(String loginId, String clothesName) throws ParseException {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        return clothesService.createClothes(user, clothesName);
    }

    @Override
    public Clothes getClothes(String loginId, String clothesName) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        return user.getClothesByClothesName(clothesName);
    }

    @Override
    public List<Clothes> getAllClothes(String loginId) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        return new ArrayList<>(user.getClothesList().values());
    }

    @Override
    public Clothes saveClothes(String loginId, String clothesName) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        Clothes clothes = user.getClothesByClothesName(clothesName);
        return clothesService.saveClothes(clothes.getSystemClothesId());
    }

    @Override
    public void deleteClothes(String loginId, String clothesName) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return;
        }
        Clothes clothes = user.getClothesByClothesName(clothesName);
        clothesService.deleteClothes(clothes.getSystemClothesId());
    }

    @Override
    public Clothes changeClothesName(String loginId, String oldClothesName, String newClothesName) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        Clothes clothes = user.getClothesByClothesName(oldClothesName);
        return clothesService.changeClothesName(clothes.getSystemClothesId(), newClothesName);
    }

    // Image
    @Override
    public Image uploadUserImage(String loginId, MultipartFile uploadImage, String clothesName) throws IOException {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        Clothes clothes = user.getClothesByClothesName(clothesName);
        return clothesService.uploadUserImage(clothes.getSystemClothesId(), uploadImage, clothesName);
    }

    @Override
    public Image createAiImageByPrompt(String loginId, String clothesName, String style, String prompt) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        Clothes clothes = user.getClothesByClothesName(clothesName);
        return clothesService.createAiImageByPrompt(clothes.getSystemClothesId(), clothesName, style, prompt);
    }

    @Override
    public Image removeImageBackground(String loginId, String clothesName, String fileUrl) {
        User user = getUserByLoginId(loginId);
        if (user == null) {
            return null;
        }
        Clothes clothes = user.getClothesByClothesName(clothesName);
        return clothesService.removeImageBackground(clothes.getSystemClothesId(), clothesName, fileUrl);
    }
}
