package com.gamechanger.dto.front.image;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageRequestByPromptDto {
    private String clothesName;
    private String imageId;
    private String designStyle;
    private String prompt;
}
