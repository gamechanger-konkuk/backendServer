package com.gamechanger.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ADMIN", "관리자"),
    USER("USER", "사용자");

    private final String key;
    private final String title;
}
