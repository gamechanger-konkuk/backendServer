package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.util.ClothesUtils;
import com.gamechanger.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;

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
            imageService.deleteImage(image.getFileName());
        }
        String roomId = clothes.getRoomId();
        liveblocksService.deleteRoom(roomId);
        clothesRepository.deleteByClothesName(clothesName);
        log.info("Clothes Name: {}, Room id: {} deleted.", clothesName, roomId);
    }

    @Override
    public Image createAiImageByPrompt(String clothesName, String style, String prompt) {
        log.info("Clothes: {}, Image Style: {}, Prompt: {} received.", clothesName, style, prompt);
        Clothes clothes = clothesRepository.findByClothesName(clothesName).get();
        Image aiImageByPrompt = imageService.createAiImageByPrompt(clothes, style, prompt);
        clothes.addImageFile(aiImageByPrompt);
        return aiImageByPrompt;
    }

    @Override
    public Image removeImageBackground(String clothesName, String fileUrl) {
        log.info("Clothes: {}, File: {} remove background.", clothesName, FileUtils.getFileNameFromUrl(fileUrl));
        Clothes clothes = clothesRepository.findByClothesName(clothesName).get();
        Image removedImage = imageService.removeImageBackground(clothes, fileUrl);
        clothes.addImageFile(removedImage);
        return removedImage;
    }
}
