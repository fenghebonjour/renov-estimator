package com.renovSolution.renov.service;

import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.*;
import com.renovSolution.renov.repo.ClientRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService {
    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }

    public Client findClientById(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Client by ID " + id + " not found"));
    }

    public Client updateClient(Client client) {
        return clientRepo.save(client);
    }

    public Client addClient(Client client) {
        return clientRepo.save(client);
    }

    public void deleteClient(Long id) {
        clientRepo.deleteClientById(id);
    }

    public Client addProjectBidToClient(ProjectBid projectBid, Long id) {
        ProjectBid bid = new ProjectBid(
                projectBid.getRequestDate(),
                projectBid.getDeadline(),
                projectBid.getWorkStartDate(),
                projectBid.getWorkEndDate(),
                projectBid.getStatus(),
                projectBid.getType()
        );
        Client client = clientRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Client by ID " + id + " not found"));
        client.addProjectBid(bid);
        return clientRepo.save(client);
    }
}
