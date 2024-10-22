package com.gamechanger.controller;

import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinIdCheckRequest;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.JoinResponse;
import com.gamechanger.dto.user.LoginRequest;
import com.gamechanger.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join/id-check")
    public ResponseEntity<String> checkIdDuplicate(@RequestBody JoinIdCheckRequest joinIdCheckRequest) {
        // 아이디 중복 확인 버튼을 위한 별개의 api
        // 중복 확인을 했는지 여부는 프론트에서 확인?
        if (userService.checkLoginIdDuplicate(joinIdCheckRequest.getLoginId())) {
            return ResponseEntity.status(409).body("해당 아이디가 이미 존재합니다.");
        }
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequest joinRequest) {
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            return ResponseEntity.badRequest().body("비밀번호가 동일하지 않습니다.");
        }
        User joinedUser = userService.join(joinRequest);
        if (joinedUser == null) {
            return ResponseEntity.status(409).body("해당 아이디가 이미 존재합니다.");
        }
        JoinResponse response = JoinResponse.builder()
                .loginId(joinedUser.getLoginId())
                .userName(joinedUser.getUserName())
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
