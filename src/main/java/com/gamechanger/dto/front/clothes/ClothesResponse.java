package com.gamechanger.dto.front.clothes;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothesResponse {
    private String roomId;
    private String clothesName;
    private String createdAt;
    private String modifiedAt;
}
