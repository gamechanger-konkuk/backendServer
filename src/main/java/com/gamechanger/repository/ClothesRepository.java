package com.gamechanger.repository;

import com.gamechanger.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, String> {
    Optional<Clothes> findByClothesName(String clothesName);
    void deleteByClothesName(String clothesName);
    @Modifying
    @Query("UPDATE Clothes c SET c.clothesName = :newClothesName WHERE c.clothesName = :oldClothesName")
    void changeClothesName(String oldClothesName, String newClothesName);
}
