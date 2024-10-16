package com.gamechanger.service;

import com.gamechanger.domain.User;
import com.gamechanger.dto.user.JoinRequest;
import com.gamechanger.dto.user.LoginRequest;
import com.gamechanger.repository.UserRepository;
import com.gamechanger.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ClothesService clothesService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User join(JoinRequest joinRequest) {
        // 중복 id 존재 시 null 반환
        if (checkLoginIdDuplicate(joinRequest.getLoginId())) {
            return null;
        }
        return userRepository.save(joinRequest.toEntity(encoder.encode(joinRequest.getPassword())));
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginRequest.getLoginId());
        // id에 해당하는 유저가 없으면 null 반환
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        // 비밀번호가 틀리면 null 반환
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        return jwtTokenProvider.createToken(user.getLoginId(), user.getName());
    }

    @Override
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElse(null);
    }

    @Override
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
