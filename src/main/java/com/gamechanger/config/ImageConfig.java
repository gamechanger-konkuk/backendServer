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

    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final AiClient aiClient;

    @Autowired
    public ImageConfig(ImageRepository imageRepository, FileService fileService, AiClient aiClient) {
        this.imageRepository = imageRepository;
        this.fileService = fileService;
        this.aiClient = aiClient;
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl(imageRepository, fileService, aiClient);
    }
}
