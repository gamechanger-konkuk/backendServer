package com.gamechanger.dto.ai.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendPromptRequestToAi {
    private String user_prompt;
    private int recommend_size;
}
