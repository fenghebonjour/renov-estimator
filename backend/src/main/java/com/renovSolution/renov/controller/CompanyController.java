package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.Company;
import com.renovSolution.renov.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.findAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") Long id) {
        Company company = companyService.findCompanyById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        Company newCompany = companyService.addCompany(company);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company updatedCompany = companyService.updateCompany(company);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
