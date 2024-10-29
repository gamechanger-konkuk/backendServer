package com.gamechanger.repository;

import com.gamechanger.domain.Clothes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, String> {
    Optional<Clothes> findBySystemClothesId(Long systemClothesId);
    Optional<Clothes> findByClothesName(String clothesName);
    @Transactional
    void deleteBySystemClothesId(Long systemClothesId);
}
