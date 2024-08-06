package com.gamechanger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemUserId;

    @Column(unique = true, length = 10)
    private String userId;

    @JsonIgnore
    private String password;
    private String userName;

    @Column(unique = true, length = 15)
    private String nickname;
    private String address;
    private int phoneNumber;
    private String email;
    private UserRole role;

    @OneToMany
    private List<Clothes> clothesList;
}
