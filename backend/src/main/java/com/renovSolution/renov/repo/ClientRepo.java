package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client,Long> {

    void deleteClientById(Long id);
}
