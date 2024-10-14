package com.gamechanger.service;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .roomId(String.valueOf(UUID.randomUUID()))
                .background("white")
                .shape("half")
                .imageFileList(new ArrayList<>())
                .build();
        String[] defaultAccesses = {"room:write"};
        String responseRoomId = liveblocksService.createRoom(clothes.getRoomId(), defaultAccesses);
        // 룸 생성은 컨트롤러에서 하던지, save를 먼저 하던지 해야 할 듯?
        // NotNull이나 컬럼 길이 제한 같은게 레포에 save할 때 검사하는 것 같은데, 룸을 먼저 생성하니까 룸은 생성되고 옷은 생성 안되게 됨.
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
    public Clothes changeClothesName(String oldClothesName, String newClothesName) {
        clothesRepository.changeClothesName(oldClothesName, newClothesName);
        Optional<Clothes> changedClothes = clothesRepository.findByClothesName(newClothesName);
        if (changedClothes.isPresent()) {
            log.info("Clothes: {} name changed to {}.", oldClothesName, newClothesName);
            return changedClothes.get();
        }
        return null;
    }

    @Override
    public Image uploadUserImage(MultipartFile uploadImage, String clothesName) throws IOException {
        Optional<Clothes> findClothes = clothesRepository.findByClothesName(clothesName);
        if (findClothes.isPresent()) {
            Clothes clothes = findClothes.get();
            Image uploadedImage = imageService.uploadImage(uploadImage.getBytes(), "front");
            clothes.addImageFile(uploadedImage);
            return uploadedImage;
        }
        return null;
    }

    @Override
    public Image createAiImageByPrompt(String clothesName, String style, String prompt) {
        log.info("Clothes: {}, Image Style: {}, Prompt: {} received.", clothesName, style, prompt);
        Optional<Clothes> findClothes = clothesRepository.findByClothesName(clothesName);
        if (findClothes.isPresent()) {
            Clothes clothes = findClothes.get();
            Image aiImageByPrompt = imageService.createAiImageByPrompt(clothes, style, prompt);
            clothes.addImageFile(aiImageByPrompt);
            return aiImageByPrompt;
        }
        return null;
    }

    @Override
    public Image removeImageBackground(String clothesName, String fileUrl) {
        Optional<Clothes> findClothes = clothesRepository.findByClothesName(clothesName);
        if (findClothes.isPresent()) {
            Clothes clothes = findClothes.get();
            Image removedImage = imageService.removeImageBackground(clothes, fileUrl);
            log.info("Clothes: {}, File: {} remove background.", clothesName, FileUtils.getFileNameFromUrl(fileUrl));
            clothes.addImageFile(removedImage);
            return removedImage;
        }
        return null;
    }
}
