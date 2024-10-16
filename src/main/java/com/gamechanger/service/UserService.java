package com.gamechanger.service;

import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.LoginRequest;

public interface UserService {
    // User
    User join(JoinRequest joinRequest);
    String login(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    boolean checkLoginIdDuplicate(String loginId);
}
