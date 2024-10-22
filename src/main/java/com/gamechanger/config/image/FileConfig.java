package com.gamechanger.config.image;

import com.gamechanger.service.image.AwsS3FileServiceImpl;
import com.gamechanger.service.image.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class FileConfig {

    private final S3Client s3Client;

    @Autowired
    public FileConfig(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Bean
    public FileService fileService() {
        return new AwsS3FileServiceImpl(s3Client);
    }
}
