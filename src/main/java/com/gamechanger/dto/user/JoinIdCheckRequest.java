package com.gamechanger.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinIdCheckRequest {

    @NotNull(message = "아이디/이메일을 입력해주세요.")
    private String loginId;
}
