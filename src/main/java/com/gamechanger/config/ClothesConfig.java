package com.gamechanger.config;

import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.service.ClothesService;
import com.gamechanger.service.ClothesServiceImpl;
import com.gamechanger.service.ImageService;
import com.gamechanger.service.LiveblocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClothesConfig {

    private final ClothesRepository clothesRepository;
    private final ImageService imageService;
    private final LiveblocksService liveblocksService;

    @Autowired
    public ClothesConfig(ClothesRepository clothesRepository, ImageService imageService, LiveblocksService liveblocksService) {
        this.clothesRepository = clothesRepository;
        this.imageService = imageService;
        this.liveblocksService = liveblocksService;
    }

    public ClothesService clothesService() {
        return new ClothesServiceImpl(clothesRepository, imageService, liveblocksService);
    }
}
