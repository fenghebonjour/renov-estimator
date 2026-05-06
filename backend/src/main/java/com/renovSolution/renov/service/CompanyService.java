package com.renovSolution.renov.service;

import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Company;
import com.renovSolution.renov.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public List<Company> findAllCompanies() {
        return companyRepo.findAll();
    }

    public Company findCompanyById(Long id) {
        return companyRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Company by ID " + id + " not found"));
    }

    public Company updateCompany(Company company) {
        return companyRepo.save(company);
    }

    public Company addCompany(Company company) {
        return companyRepo.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepo.deleteCompanyById(id);
    }
}
