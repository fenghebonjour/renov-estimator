package com.renovSolution.renov.service;

import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Labor;
import com.renovSolution.renov.repo.LaborRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LaborService {

    private final LaborRepo laborRepo;

    @Autowired
    public LaborService(LaborRepo laborRepo) {
        this.laborRepo = laborRepo;
    }

    public List<Labor> findAllLabors() {
        return laborRepo.findAll();
    }

    public Labor findLaborById(Long id) {
        return laborRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Labor by ID " + id + " not found"));
    }

    public Labor updateLabor(Labor labor) {
        return laborRepo.save(labor);
    }

    public Labor addLabor(Labor labor) {
        return laborRepo.save(labor);
    }

    public void deleteLabor(Long id) {
        laborRepo.deleteLaborById(id);
    }
}
