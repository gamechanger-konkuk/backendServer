package com.gamechanger.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String loginId;
    private String userName;
    private String provider;
    private String role;
    private String accessToken;
}
