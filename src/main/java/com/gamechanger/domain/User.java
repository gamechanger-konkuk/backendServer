package com.gamechanger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long systemUserId;

    @Column(unique = true)
    @NotNull(message = "아이디를 입력해야 합니다.")
    private String loginId;

    @JsonIgnore
    @NotNull(message = "비밀번호를 입력해야 합니다.")
    private String password;    // 암호화되어 저장

    @Column(length = 30)
    @NotNull(message = "이름을 입력해야 합니다.")
    private String userName;

    private String provider;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapKey(name = "clothesName")
    private Map<String, Clothes> clothesList = new HashMap<>();

    public Clothes getClothesByClothesName(String clothesName) {
        return clothesList.get(clothesName);
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
