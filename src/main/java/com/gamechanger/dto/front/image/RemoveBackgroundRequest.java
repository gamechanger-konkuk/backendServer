package com.gamechanger.dto.front.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBackgroundRequest {
    private String clothesName;
    private String imageId;
    private String imageUrl;
}
