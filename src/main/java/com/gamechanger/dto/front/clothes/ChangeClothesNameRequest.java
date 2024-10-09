package com.gamechanger.dto.front.clothes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeClothesNameRequest {
    private String oldClothesName;
    private String newClothesName;
}
