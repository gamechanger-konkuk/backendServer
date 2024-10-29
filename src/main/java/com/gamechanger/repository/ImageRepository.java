package com.gamechanger.repository;

import com.gamechanger.domain.Image;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByFileName(String fileName);
    @Query("SELECT i FROM Image i WHERE i.clothes.systemClothesId = :systemClothesId")
    List<Image> findImagesBySystemClothesId(@Param("systemClothesId") Long systemClothesId);
    @Transactional
    void deleteByFileName(String fileName);
    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.clothes.systemClothesId = :systemClothesId")
    void deleteImagesBySystemClothesId(@Param("systemClothesId") Long systemClothesId);
}
