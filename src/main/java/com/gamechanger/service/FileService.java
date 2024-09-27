package com.gamechanger.service;

public interface FileService {
    String uploadFile(byte[] image);
    byte[] downloadFile(String fileUrl);
    void deleteFile(String fileName);
}
