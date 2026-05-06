package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Individual;
import com.renovSolution.renov.repo.IndividualRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndividualService {

    private final IndividualRepo individualRepo;

    @Autowired
    public IndividualService(IndividualRepo individualRepo) {
        this.individualRepo = individualRepo;
    }

    public List<Individual> findAllIndividuals() {
        return individualRepo.findAll();
    }

    public Individual findIndividualById(Long id) {
        return individualRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Individual by ID " + id + " not found"));
    }

    public Individual updateIndividual(Individual individual) {
        return individualRepo.save(individual);
    }

    public Individual addIndividual(Individual individual) {
        return individualRepo.save(individual);
    }

    public void deleteIndividual(Long id) {
        individualRepo.deleteIndividualById(id);
    }
}
