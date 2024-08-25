package com.gamechanger.service;

import com.gamechanger.domain.Clothes;

public interface ClothesService {
    Clothes createClothes(String userName, String shape);
    Clothes getClothes(String userName, String clothesName);
    void saveClothes(String userName, Clothes clothes);
    Clothes deleteClothes(String userName, String clothesName);
}
