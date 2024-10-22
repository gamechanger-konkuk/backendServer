package com.gamechanger.config.clothes;

import com.gamechanger.repository.ClothesRepository;
import com.gamechanger.service.clothes.ClothesService;
import com.gamechanger.service.clothes.ClothesServiceImpl;
import com.gamechanger.service.image.ImageService;
import com.gamechanger.service.clothes.LiveblocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public ClothesService clothesService() {
        return new ClothesServiceImpl(clothesRepository, imageService, liveblocksService);
    }
}
