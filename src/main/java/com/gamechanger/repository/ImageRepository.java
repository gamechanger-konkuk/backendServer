package com.gamechanger.repository;

import com.gamechanger.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByFileName(String fileName);
}
