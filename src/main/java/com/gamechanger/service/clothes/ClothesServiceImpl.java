package com.gamechanger.service.clothes;

import com.gamechanger.domain.Clothes;
import com.gamechanger.domain.Image;
import com.gamechanger.domain.User;
import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.service.image.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final ImageService imageService;
    private final LiveblocksService liveblocksService;

    @Override
    public Clothes createClothes(User user, String clothesName) throws ParseException {
        Clothes clothes = Clothes.builder()
                .clothesName(clothesName)
                .roomId(String.valueOf(UUID.randomUUID()))
                .background("white")
                .shape("half")
                .imageFileList(new HashMap<>())
                .user(user)
                .build();
        String[] defaultAccesses = {"room:write"};
        Clothes savedClothes = clothesRepository.save(clothes);
        String responseRoomId = liveblocksService.createRoom(clothes.getRoomId(), defaultAccesses);
        // 룸 생성은 컨트롤러에서 하던지, save를 먼저 하던지 해야 할 듯?
        // NotNull이나 컬럼 길이 제한 같은게 레포에 save할 때 검사하는 것 같은데, 룸을 먼저 생성하니까 룸은 생성되고 옷은 생성 안되게 됨.
        log.info("Clothes Name: {}, Room Id: {} created.", clothesName, responseRoomId);
        return savedClothes;
    }

    @Override
    public Optional<Clothes> getClothes(Long systemClothesId) {
        return clothesRepository.findBySystemClothesId(systemClothesId);
    }

    @Override
    public List<Clothes> getAllClothes() {
        return clothesRepository.findAll();
    }

    @Override
    public Clothes saveClothes(Long systemClothesId) {
        Optional<Clothes> optionalClothes = clothesRepository.findBySystemClothesId(systemClothesId);
        if (optionalClothes.isEmpty()) {
            return null;
        }
        Clothes clothes = optionalClothes.get();
        // 해당 티셔츠의 룸 getStorage하고
        String updatedStorage = liveblocksService.getStorage(clothes.getRoomId());
        // 가져온 내용으로 위의 clothes 내용 업데이트하기

        // 이후 레포에 해당 clothes 저장
        return clothesRepository.save(clothes);
    }

    @Override
    public void deleteClothes(Long systemClothesId) {
        Clothes clothes = clothesRepository.findBySystemClothesId(systemClothesId).get();
        for (Image image : clothes.getImageFileList().values()) {
            imageService.deleteImage(image.getFileName());
        }
        String roomId = clothes.getRoomId();
        liveblocksService.deleteRoom(roomId);
        clothesRepository.deleteBySystemClothesId(systemClothesId);
        log.info("Clothes Name: {}, Room id: {} deleted.", clothes.getClothesName(), roomId);
    }

    @Override
    public Clothes changeClothesName(Long systemClothesId, String newClothesName) {
        Clothes clothes = clothesRepository.findBySystemClothesId(systemClothesId)
                .orElseThrow(() -> new EntityNotFoundException("Clothes not found"));
        clothes.setClothesName(newClothesName);
        clothesRepository.save(clothes);
        return clothesRepository.findBySystemClothesId(systemClothesId).orElse(null);
    }

    @Override
    public Image uploadUserImage(Long systemClothesId, MultipartFile uploadImage, String clothesName) throws IOException {
        Clothes clothes = clothesRepository.findBySystemClothesId(systemClothesId).get();
        Image uploadedImage = imageService.uploadImage(clothes, uploadImage.getBytes(), "front");
        clothes.addImageFile(uploadedImage);
        return uploadedImage;
    }

    @Override
    public Image createAiImageByPrompt(Long systemClothesId, String clothesName, String style, String prompt) {
        Clothes clothes = clothesRepository.findBySystemClothesId(systemClothesId).get();
        Image aiImageByPrompt = imageService.createAiImageByPrompt(clothes, style, prompt);
        clothes.addImageFile(aiImageByPrompt);
        return aiImageByPrompt;
    }

    @Override
    public Image removeImageBackground(Long systemClothesId, String clothesName, String fileUrl) {
        Clothes clothes = clothesRepository.findBySystemClothesId(systemClothesId).get();
        Image removedImage = imageService.removeImageBackground(clothes, fileUrl);
        clothes.addImageFile(removedImage);
        return removedImage;
    }
}
