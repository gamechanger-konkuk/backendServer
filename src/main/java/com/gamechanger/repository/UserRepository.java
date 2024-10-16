package com.gamechanger.repository;

import com.gamechanger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByLoginId(String loginId);
    Optional<User> findByLoginId(String loginId);
}
