package com.gamechanger.repository;

import com.gamechanger.domain.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<Design, String> {
}
