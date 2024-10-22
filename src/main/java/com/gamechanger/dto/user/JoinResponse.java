package com.gamechanger.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponse {
    private String loginId;
    private String userName;
    private String provider;
    private String role;
}
