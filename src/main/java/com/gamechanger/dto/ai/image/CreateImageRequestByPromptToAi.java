package com.gamechanger.dto.ai.image;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageRequestByPromptToAi {
    @NotNull
    private String style;
    @NotNull
    private String text_prompt;
}
