package com.gamechanger.controller;

import com.gamechanger.domain.Clothes;
import com.gamechanger.dto.front.clothes.CreateClothesInputDto;
import com.gamechanger.dto.front.clothes.CreateClothesOutputDto;
import com.gamechanger.service.ClothesService;
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
    public ResponseEntity<CreateClothesOutputDto> createClothes(@RequestBody CreateClothesInputDto createClothesInputDto) throws ParseException {
        String clothesName = createClothesInputDto.getClothesName();
        Clothes clothes = clothesService.createClothes(clothesName);
        CreateClothesOutputDto response = CreateClothesOutputDto.builder()
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
    public ResponseEntity<List<CreateClothesOutputDto>> viewAllClothes() {
        List<Clothes> allClothes = clothesService.getAllClothes();
        if (allClothes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CreateClothesOutputDto> responseAllClothes = new ArrayList<>();
        for (Clothes c : allClothes) {
            CreateClothesOutputDto response = CreateClothesOutputDto.builder()
                    .roomId(c.getRoomId())
                    .clothesName(c.getClothesName())
                    .createdAt(c.getCreatedAt().toString())
                    .modifiedAt(c.getModifiedAt().toString())
                    .build();
            responseAllClothes.add(response);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseAllClothes);
    }

    @GetMapping("/name/{clothesName}")
    public ResponseEntity<CreateClothesOutputDto> viewOneClothes(@PathVariable("clothesName") String clothesName) {
        Optional<Clothes> clothes = clothesService.getClothes(clothesName);
        if (clothes.isPresent()) {
            log.info("Clothes Name: {} found.", clothesName);
            Clothes findClothes = clothes.get();
            CreateClothesOutputDto response = CreateClothesOutputDto.builder()
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

}
