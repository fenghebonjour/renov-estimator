package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepo extends JpaRepository<Material, Long> {
    void deleteMaterialById(Long id);
}
