package com.gamechanger.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponse {
    private String loginId;
    private String name;
    private String provider;
    private String role;
}
