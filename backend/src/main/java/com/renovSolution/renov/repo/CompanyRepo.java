package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    void deleteCompanyById(Long id);
}
