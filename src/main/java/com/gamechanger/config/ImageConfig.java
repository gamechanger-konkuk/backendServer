package com.gamechanger.config;

import com.gamechanger.client.AiClient;
import com.gamechanger.repository.ImageRepository;
import com.gamechanger.service.FileService;
import com.gamechanger.service.ImageService;
import com.gamechanger.service.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ImageConfig {

    private final AiClient aiClient;
    private final FileService fileService;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageConfig(AiClient aiClient, FileService fileService, ImageRepository imageRepository) {
        this.aiClient = aiClient;
        this.fileService = fileService;
        this.imageRepository = imageRepository;
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl(aiClient, fileService, imageRepository);
    }
}
