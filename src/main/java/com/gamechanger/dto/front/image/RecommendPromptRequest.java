package com.gamechanger.dto.front.image;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendPromptRequest {
    @NotNull
    private String prompt;
}
