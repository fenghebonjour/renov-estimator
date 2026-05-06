package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualRepo extends JpaRepository<Individual, Long> {
    void deleteIndividualById(Long id);
}
