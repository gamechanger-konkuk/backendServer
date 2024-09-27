package com.gamechanger.service;

import com.gamechanger.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AwsS3FileServiceImpl implements FileService {
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(byte[] image) {
        String fileName = makeRandomFileName();
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .contentType("image/jpeg")
                    .contentLength((long) image.length)
                    .key(fileName)
                    .build();
            RequestBody requestBody = RequestBody.fromBytes(image);
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            log.error("cannot upload image",e);
            throw new RuntimeException(e);
        }
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest).toString(); // 파일 url 반환하는 것
    }

    @Override
    public byte[] downloadFile(String fileUrl) {
        String fileName = FileUtils.getFileNameFromUrl(fileUrl);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);
        try {
            return object.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public String makeRandomFileName() {
        return UUID.randomUUID() + ".jpeg";
    }
}
