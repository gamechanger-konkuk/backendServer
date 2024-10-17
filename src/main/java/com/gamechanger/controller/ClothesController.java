package com.gamechanger.controller;

import com.gamechanger.domain.Clothes;
import com.gamechanger.dto.front.clothes.ChangeClothesNameRequest;
import com.gamechanger.dto.front.clothes.CreateClothesRequest;
import com.gamechanger.dto.front.clothes.ClothesResponse;
import com.gamechanger.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

import static com.gamechanger.util.jwt.JwtUtils.getCurrentLoginId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createClothes(@Valid @RequestBody CreateClothesRequest createClothesRequest) throws ParseException {
        String loginId = getCurrentLoginId();
        String clothesName = createClothesRequest.getClothesName();
        // 티셔츠 중복 검사
        if (userService.getClothes(loginId, clothesName) != null) {
            // 409 conflict
            log.info("사용자 {}에게 티셔츠 {}이 이미 존재합니다.", loginId, clothesName);
            return ResponseEntity.status(409).body("중복된 이름의 티셔츠가 존재합니다.");
        }
        Clothes clothes = userService.createClothes(loginId, clothesName);
        log.info("사용자 {}에게 티셔츠 {}이 생성되었습니다.", loginId, clothesName);
        ClothesResponse response = ClothesResponse.builder()
                .roomId(clothes.getRoomId())
                .clothesName(clothes.getClothesName())
                .createdAt(clothes.getCreatedAt().toString())
                .modifiedAt(clothes.getModifiedAt().toString())
                .build();
        return ResponseEntity.created(URI.create("/clothes/name/" + clothesName))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Clothes>> viewAllClothes() {
        String loginId = getCurrentLoginId();
        List<Clothes> allClothes = userService.getAllClothes(loginId);
        log.info("사용자 {}의 모든 티셔츠를 조회합니다.", loginId);
        if (allClothes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allClothes);
    }

    @GetMapping("/name/{clothesName}")
    public ResponseEntity<ClothesResponse> viewOneClothes(@PathVariable("clothesName") String clothesName) {
        String loginId = getCurrentLoginId();
        Clothes clothes = userService.getClothes(loginId, clothesName);
        if (clothes == null) {
            log.info("사용자 {}에게 티셔츠 {}이 존재하지 않습니다.", loginId, clothesName);
            return ResponseEntity.notFound().build();
        }
        log.info("사용자 {}의 티셔츠 {}을 조회합니다.", loginId, clothesName);
        ClothesResponse response = ClothesResponse.builder()
                .roomId(clothes.getRoomId())
                .clothesName(clothes.getClothesName())
                .createdAt(clothes.getCreatedAt().toString())
                .modifiedAt(clothes.getModifiedAt().toString())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/name/{clothesName}")
    public ResponseEntity<Clothes> saveClothes(@PathVariable("clothesName") String clothesName) {
        String loginId = getCurrentLoginId();
        Clothes clothes = userService.saveClothes(loginId, clothesName);
        log.info("사용자 {}의 티셔츠 {}을 저장합니다.", loginId, clothesName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clothes);
    }

    @DeleteMapping("/name/{clothesName}")
    public ResponseEntity<String> deleteClothes(@PathVariable("clothesName") String clothesName) {
        String loginId = getCurrentLoginId();
        userService.deleteClothes(loginId, clothesName);
        log.info("사용자 {}의 티셔츠 {}을 삭제합니다.", loginId, clothesName);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/change-name")
    public ResponseEntity<?> changeClothesName(@RequestBody ChangeClothesNameRequest request) {
        String loginId = getCurrentLoginId();
        String oldClothesName = request.getOldClothesName();
        String newClothesName = request.getNewClothesName();
        if (userService.getClothes(loginId, newClothesName) != null) {
            log.info("사용자 {}에게 티셔츠 {}이 이미 존재합니다.", loginId, newClothesName);
            return ResponseEntity.status(409).body("중복된 이름의 티셔츠가 존재합니다.");
        }
        Clothes clothes = userService.changeClothesName(loginId, oldClothesName, newClothesName);
        log.info("사용자 {}의 티셔츠 {} 이름이 {}으로 변경되었습니다.", loginId, oldClothesName, newClothesName);
        ClothesResponse response = ClothesResponse.builder()
                .clothesName(clothes.getClothesName())
                .roomId(clothes.getRoomId())
                .createdAt(clothes.getCreatedAt().toString())
                .modifiedAt(clothes.getModifiedAt().toString())
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
