package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.BidMaterial;
import com.renovSolution.renov.model.ServiceOffer;
import com.renovSolution.renov.service.ServiceOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-offer")
public class ServiceOfferController {
    private final ServiceOfferService serviceOfferService;

    public ServiceOfferController(ServiceOfferService serviceOfferService) {
        this.serviceOfferService = serviceOfferService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceOffer>> getAllServiceOffers() {
        List<ServiceOffer> offers = serviceOfferService.findAllServiceOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/contractor/{id}")
    public ResponseEntity<List<ServiceOffer>> getServiceOffersByContractorId(@PathVariable("id") Long id) {
        List<ServiceOffer> offers = serviceOfferService.findServiceOffersByContractorId(id);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/bid/{id}")
    public ResponseEntity<List<ServiceOffer>> getServiceOffersByBidId(@PathVariable("id") Long id) {
        List<ServiceOffer> offers = serviceOfferService.findServiceOffersByProjectBidId(id);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ServiceOffer> addServiceOffer(@RequestBody ServiceOffer serviceOffer) {
        ServiceOffer newOffer = serviceOfferService.addServiceOffer(serviceOffer);
        return new ResponseEntity<>(newOffer, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ServiceOffer> updateServiceOffer(@RequestBody ServiceOffer serviceOffer) {
        ServiceOffer updatedOffer = serviceOfferService.updateServiceOffer(serviceOffer);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteServiceOffer(@PathVariable("id") Long id) {
        serviceOfferService.deleteServiceOffer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/material/{id}")
    public ResponseEntity<ServiceOffer> addMaterial(@RequestBody BidMaterial bidMaterial, @PathVariable("id") Long id) {
        ServiceOffer updatedOffer = serviceOfferService.addMaterial(bidMaterial, id);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }
}
