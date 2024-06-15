package com.gamechanger.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromptDto {
    @NotEmpty
    private String prompt;
}
