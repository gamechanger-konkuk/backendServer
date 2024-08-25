package com.gamechanger.repository;

import com.gamechanger.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<Clothes, String> {
}
