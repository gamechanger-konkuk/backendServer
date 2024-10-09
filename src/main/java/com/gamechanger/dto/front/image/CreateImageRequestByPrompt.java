package com.gamechanger.dto.front.image;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageRequestByPrompt {
    @NotNull
    private String clothesName;
    @NotNull
    private String imageId;
    @NotNull
    private String designStyle;
    @NotNull
    private String prompt;
}
