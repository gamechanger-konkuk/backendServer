package com.gamechanger.dto.front.clothes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CreateClothesRequest {
    @NotNull
    private String clothesName;
}
