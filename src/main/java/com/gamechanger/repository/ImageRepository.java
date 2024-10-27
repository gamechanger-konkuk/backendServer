package com.gamechanger.repository;

import com.gamechanger.domain.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByFileName(String fileName);
    @Transactional
    void deleteByFileName(String fileName);
}
