package com.gamechanger.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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
    @JsonIgnore
    private Long systemUserId;

    @Column(unique = true)
    @NotNull
    private String email;

    @JsonIgnore
    @Column(length = 16)
    @NotNull
    private String password;

    @Column(length = 30)
    @NotNull
    private String userRealName;

//    @Column(length = 20)
//    @NotNull
//    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Clothes> clothesList = new ArrayList<>();

}
