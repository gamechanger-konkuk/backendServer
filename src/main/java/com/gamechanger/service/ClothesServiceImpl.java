package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.util.ClothesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final ImageService imageService;
    private final LiveblocksService liveblocksService;

    @Override
    public Clothes createClothes(String clothesName) throws ParseException {
        Clothes clothes = Clothes.builder()
                .clothesName(clothesName)
                .roomId(ClothesUtils.makeRoomIdBysClothesName(clothesName))
                .background("white")
                .shape("half")
                .imageFileList(new ArrayList<>())
                .build();
        String[] defaultAccesses = new String[1];
        defaultAccesses[0] = "room:write";
        String responseRoomId = liveblocksService.createRoom(clothes.getRoomId(), defaultAccesses);
        log.info("Clothes Name: {}, Room Id: {} created.", clothesName, responseRoomId);
        return clothesRepository.save(clothes);
    }

    @Override
    public Optional<Clothes> getClothes(String clothesName) {
        return clothesRepository.findByClothesName(clothesName);
    }

    @Override
    public List<Clothes> getAllClothes() {
        return clothesRepository.findAll();
    }

    @Override
    public Clothes saveClothes(String clothesName) {
        Clothes clothes = clothesRepository.findByClothesName(clothesName).get();
        // 해당 티셔츠의 룸 getStorage하고
        String updatedStorage = liveblocksService.getStorage(clothes.getRoomId());
        // 가져온 내용으로 위의 clothes 내용 업데이트하기

        // 이후 레포에 해당 clothes 저장
        Clothes saveClothes = clothesRepository.save(clothes);
        log.info("Clothes Name: {} saved.", clothes.getClothesName());
        return saveClothes;
    }

    @Override
    public void deleteClothes(String clothesName) {
        Clothes clothes = clothesRepository.findByClothesName(clothesName).get();
        for (Image image : clothes.getImageFileList()) {
            imageService.deleteImage(image.getFileUrl());
        }
        String roomId = clothes.getRoomId();
        liveblocksService.deleteRoom(roomId);
        clothesRepository.deleteById(clothesName);
        log.info("Clothes Name: {}, Room id: {} deleted.", clothesName, roomId);
    }

    @Override
    public Image createAiImageByPrompt(String clothesName, String style, String prompt) {
        Image aiImageByPrompt = imageService.createAiImageByPrompt(style, prompt);
        clothesRepository.findByClothesName(clothesName).ifPresent(c -> c.addImageFile(aiImageByPrompt));
        return aiImageByPrompt;
    }

    @Override
    public Image removeImageBackground(String clothesName, String fileUrl) {
        Image removedImage = imageService.removeImageBackground(fileUrl);
        clothesRepository.findByClothesName(clothesName).ifPresent(c -> c.addImageFile(removedImage));
        return removedImage;
    }
}
