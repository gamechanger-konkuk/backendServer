package com.gamechanger.controller;

import com.gamechanger.domain.Clothes;
import com.gamechanger.dto.front.clothes.ChangeClothesNameRequest;
import com.gamechanger.dto.front.clothes.CreateClothesRequest;
import com.gamechanger.dto.front.clothes.ClothesResponse;
import com.gamechanger.service.ClothesService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {

    private final ClothesService clothesService;

    @PostMapping("/create")
    public ResponseEntity<ClothesResponse> createClothes(@Valid @RequestBody CreateClothesRequest createClothesRequest) throws ParseException {
        String clothesName = createClothesRequest.getClothesName();
        Clothes clothes = clothesService.createClothes(clothesName);
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
        List<Clothes> allClothes = clothesService.getAllClothes();
        if (allClothes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allClothes);
    }

    @GetMapping("/name/{clothesName}")
    public ResponseEntity<ClothesResponse> viewOneClothes(@PathVariable("clothesName") String clothesName) {
        Optional<Clothes> clothes = clothesService.getClothes(clothesName);
        if (clothes.isPresent()) {
            log.info("Clothes Name: {} found.", clothesName);
            Clothes findClothes = clothes.get();
            ClothesResponse response = ClothesResponse.builder()
                    .roomId(findClothes.getRoomId())
                    .clothesName(findClothes.getClothesName())
                    .createdAt(findClothes.getCreatedAt().toString())
                    .modifiedAt(findClothes.getModifiedAt().toString())
                    .build();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/name/{clothesName}")
    public ResponseEntity<Clothes> saveClothes(@PathVariable("clothesName") String clothesName) {
        Clothes saveClothes = clothesService.saveClothes(clothesName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(saveClothes);
    }

    @DeleteMapping("/name/{clothesName}")
    public ResponseEntity<String> deleteClothes(@PathVariable("clothesName") String clothesName) {
        clothesService.deleteClothes(clothesName);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PutMapping("/change-name")
    public ResponseEntity<ClothesResponse> changeClothesName(@RequestBody ChangeClothesNameRequest request) {
        Clothes clothes = clothesService.changeClothesName(request.getOldClothesName(), request.getNewClothesName());
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
