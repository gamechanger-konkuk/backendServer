package com.gamechanger.dto.ai.image;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageRequestByPromptToAi {
    private String style;
    private String text_prompt;
}
