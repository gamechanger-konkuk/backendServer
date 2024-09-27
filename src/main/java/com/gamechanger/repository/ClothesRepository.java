package com.gamechanger.repository;

import com.gamechanger.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, String> {
    Optional<Clothes> findByClothesName(String clothesName);
}
