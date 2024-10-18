package com.gamechanger.dto.user;

import com.gamechanger.domain.User;
import com.gamechanger.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

    @NotNull(message = "아이디/이메일을 입력해주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
    private String passwordCheck;

    @NotNull(message = "이름을 입력해주세요.")
    private String userName;

    // 비밀번호 암호화 X
    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .userName(this.userName)
                .provider("JWT")
                .role(UserRole.USER)
                .build();
    }

    // 비밀번호 암호화
    public User toEntity(String encodedPassword) {
        return User.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .userName(this.userName)
                .provider("JWT")
                .role(UserRole.USER)
                .build();
    }
}
