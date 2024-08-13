package com.gamechanger.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile multipartFile);
    MultipartFile downloadFile(String fileName);
    String getFileName(MultipartFile multipartFile);
}
