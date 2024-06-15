package com.gamechanger.service;

public interface DesignService {
    String createAIDesign(String prompt);
    byte[] getImage(String id);
}
