package com.gamechanger.util;

import java.util.UUID;

public class ClothesUtils {
    public static String makeRoomIdBysClothesName(String clothesName) {
        return clothesName + "-" + UUID.randomUUID();
    }
}
