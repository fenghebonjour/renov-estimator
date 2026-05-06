package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Labor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaborRepo extends JpaRepository<Labor, Long> {
    void deleteLaborById(Long id);
}
