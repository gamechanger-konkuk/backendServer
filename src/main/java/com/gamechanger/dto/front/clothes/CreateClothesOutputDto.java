package com.gamechanger.dto.front.clothes;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClothesOutputDto {
    private String roomId;
    private String clothesName;
    private String createdAt;
    private String modifiedAt;
}
