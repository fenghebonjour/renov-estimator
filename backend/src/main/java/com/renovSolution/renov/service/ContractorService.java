package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Contractor;
import com.renovSolution.renov.model.ProjectBid;
import com.renovSolution.renov.model.ServiceOffer;
import com.renovSolution.renov.repo.ContractorRepo;
import com.renovSolution.renov.repo.ProjectBidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractorService {
    private final ContractorRepo contractorRepo;
    private final ProjectBidRepo projectBidRepo;

    @Autowired
    public ContractorService(ContractorRepo contractorRepo, ProjectBidRepo projectBidRepo) {
        this.contractorRepo = contractorRepo;
        this.projectBidRepo = projectBidRepo;
    }

    public List<Contractor> findAllContractors() {
        return contractorRepo.findAll();
    }

    public Contractor findContractorById(Long id) {
        return contractorRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Contractor by ID " + id + " not found"));
    }

    public Contractor updateContractor(Contractor contractor) {
        return contractorRepo.save(contractor);
    }

    public Contractor addContractor(Contractor contractor) {
        return contractorRepo.save(contractor);
    }

    public void deleteContractor(Long id) {
        contractorRepo.deleteById(id);
    }

    public Contractor addServiceOfferToContractor(ServiceOffer serviceOffer, Long contractorId, Long bidId) {
        ServiceOffer offer = new ServiceOffer(
                serviceOffer.getOfferDate(),
                serviceOffer.getValidUntil(),
                serviceOffer.getStatus(),
                serviceOffer.getAmount()
        );
        Contractor contractor = contractorRepo.findById(contractorId).orElseThrow(() -> new UserNotFoundException("Contractor by ID " + contractorId + " not found"));
        ProjectBid projectBid = projectBidRepo.findById(bidId).orElseThrow(() -> new UserNotFoundException("Project Bid by ID " + bidId + " not found"));

        projectBid.addServiceOffer(offer);
        contractor.addServiceOffer(offer);
        return contractorRepo.save(contractor);
    }
}
