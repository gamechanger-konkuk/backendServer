package com.gamechanger.controller;

import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.JoinResponse;
import com.gamechanger.dto.user.LoginRequest;
import com.gamechanger.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        User joinedUser = userService.join(joinRequest);
        if (joinedUser == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "아이디가 이미 존재합니다.");
        }
        JoinResponse response = JoinResponse.builder()
                .loginId(joinedUser.getLoginId())
                .name(joinedUser.getName())
                .provider(joinedUser.getProvider())
                .role(joinedUser.getRoleKey())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String userJwtToken = userService.login(loginRequest);
        if (userJwtToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못됐습니다.");
        }
        return ResponseEntity.ok(userJwtToken);
    }
}
