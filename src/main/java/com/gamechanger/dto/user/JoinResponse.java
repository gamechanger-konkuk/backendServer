package com.gamechanger.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class JoinResponse {
    private String loginId;
    private String name;
    private String provider;
    private String role;
}
