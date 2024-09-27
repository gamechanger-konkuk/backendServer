//package com.gamechanger.dto.user;
//
//import com.gamechanger.domain.User;
//import com.gamechanger.domain.UserRole;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class JoinRequestDto {
//
//    @NotBlank(message = "아이디를 입력해주세요.")
//    private String userId;
//
//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Min(8)
//    private String password;
//    private String passwordCheck;
//
//    @NotBlank(message = "이름을 입력해주세요.")
//    private String userName;
//
//    @NotBlank(message = "닉네임을 입력해주세요.")
//    private String nickname;
//
//    @NotBlank(message = "주소를 입력해주세요.")
//    private String address;
//
//    @NotBlank(message = "전화번호를 입력해주세요.")
//    private int phoneNumber;
//
//    @NotBlank(message = "이메일을 입력해주세요.")
//    @Email
//    private String email;
//
//    // 비밀번호 암호화 X
//    public User toEntity() {
//        return User.builder()
//                .userId(this.userId)
//                .password(this.password)
//                .nickname(this.nickname)
//                .address(this.address)
//                .phoneNumber(this.phoneNumber)
//                .email(this.email)
//                .role(UserRole.USER)
//                .build();
//    }
//
//    // 비밀번호 암호화
//    public User toEntity(String encodedPassword) {
//        return User.builder()
//                .userId(this.userId)
//                .password(encodedPassword)
//                .nickname(this.nickname)
//                .address(this.address)
//                .phoneNumber(this.phoneNumber)
//                .email(this.email)
//                .role(UserRole.USER)
//                .build();
//    }
//}
