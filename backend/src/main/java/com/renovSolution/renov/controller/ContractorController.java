package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.Contractor;
import com.renovSolution.renov.model.ServiceOffer;
import com.renovSolution.renov.service.ContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contractor")
public class ContractorController {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contractor>> getAllContractors() {
        List<Contractor> contractors = contractorService.findAllContractors();
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Contractor> getContractorById(@PathVariable("id") Long id) {
        Contractor contractor = contractorService.findContractorById(id);
        return new ResponseEntity<>(contractor, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Contractor> addContractor(@RequestBody Contractor contractor) {
        Contractor newContractor = contractorService.addContractor(contractor);
        return new ResponseEntity<>(newContractor, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Contractor> updateContractor(@RequestBody Contractor contractor) {
        Contractor updatedContractor = contractorService.updateContractor(contractor);
        return new ResponseEntity<>(updatedContractor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContractor(@PathVariable("id") Long id) {
        contractorService.deleteContractor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/addServiceOffer/{contractorId}/{bidId}")
    public ResponseEntity<Contractor> addServiceOfferToContractor(@RequestBody ServiceOffer serviceOffer,
                                                                   @PathVariable("contractorId") Long contractorId,
                                                                   @PathVariable("bidId") Long bidId) {
        Contractor updatedContractor = contractorService.addServiceOfferToContractor(serviceOffer, contractorId, bidId);
        return new ResponseEntity<>(updatedContractor, HttpStatus.OK);
    }
}
