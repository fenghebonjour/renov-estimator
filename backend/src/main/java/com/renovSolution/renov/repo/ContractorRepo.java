package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepo extends JpaRepository<Contractor, Long> {
    void deleteById(Long id);
}
