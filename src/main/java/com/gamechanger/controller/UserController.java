package com.gamechanger.controller;

import com.gamechanger.domain.User;
import com.gamechanger.dto.user.*;
import com.gamechanger.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static com.gamechanger.util.jwt.JwtUtils.getCurrentLoginId;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join/id-check")
    public ResponseEntity<String> checkIdDuplicate(@RequestBody JoinIdCheckRequest joinIdCheckRequest) {
        // 아이디 중복 확인 버튼을 위한 별개의 api
        String joinId = joinIdCheckRequest.getLoginId();
        log.info("아이디 {}의 중복확인을 진행합니다.", joinId);
        if (userService.checkLoginIdDuplicate(joinId)) {
            log.info("아이디 {}가 이미 존재합니다.", joinId);
            return ResponseEntity.status(409).body("해당 아이디가 이미 존재합니다.");
        }
        log.info("아이디 {}는 사용 가능합니다.", joinId);
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
        log.info("사용자 {}가 가입되었습니다.", joinedUser.getLoginId());
        UserResponse response = UserResponse.builder()
                .loginId(joinedUser.getLoginId())
                .userName(joinedUser.getUserName())
                .provider(joinedUser.getProvider())
                .role(joinedUser.getRoleKey())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        if (response.getAccessToken() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 잘못됐습니다.");
        }
        log.info("사용자 {}가 로그인되었습니다.", loginRequest.getLoginId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInfo() {
        String loginId = getCurrentLoginId();
        log.info("사용자 {}의 정보를 조회합니다.", loginId);
        User user = userService.getUserByLoginId(loginId);
        UserResponse response = UserResponse.builder()
                .loginId(loginId)
                .userName(user.getUserName())
                .provider(user.getProvider())
                .role(user.getRoleKey())
                .build();
        return ResponseEntity.ok(response);
    }
}
