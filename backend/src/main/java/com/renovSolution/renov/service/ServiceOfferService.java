package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.*;
import com.renovSolution.renov.repo.MaterialRepo;
import com.renovSolution.renov.repo.ServiceOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceOfferService {

    private final ServiceOfferRepo serviceOfferRepo;
    private final MaterialRepo materialRepo;

    @Autowired
    public ServiceOfferService(ServiceOfferRepo serviceOfferRepo, MaterialRepo materialRepo) {
        this.serviceOfferRepo = serviceOfferRepo;
        this.materialRepo = materialRepo;
    }

    public List<ServiceOffer> findAllServiceOffers() {
        return serviceOfferRepo.findAll();
    }

    public List<ServiceOffer> findServiceOffersByContractorId(Long id) {
        return serviceOfferRepo.findServiceOffersByContractorId(id);
    }

    public List<ServiceOffer> findServiceOffersByProjectBidId(Long id) {
        return serviceOfferRepo.findServiceOffersByProjectBidId(id);
    }

    public ServiceOffer findServiceOfferById(Long id) {
        return serviceOfferRepo.findServiceOfferById(id).orElseThrow(() -> new UserNotFoundException("Service Offer by ID " + id + " not found"));
    }

    public ServiceOffer updateServiceOffer(ServiceOffer serviceOffer) {
        return serviceOfferRepo.save(serviceOffer);
    }

    public ServiceOffer addServiceOffer(ServiceOffer serviceOffer) {
        return serviceOfferRepo.save(serviceOffer);
    }

    public void deleteServiceOffer(Long id) {
        serviceOfferRepo.deleteServiceOfferById(id);
    }

    public ServiceOffer addMaterial(BidMaterial bidMaterial, Long id) {
        Material material = materialRepo.findById(bidMaterial.getMaterial().getId()).orElseThrow(() -> new UserNotFoundException("Material not found"));
        ServiceOffer serviceOffer = serviceOfferRepo.findServiceOfferById(id).orElseThrow(() -> new UserNotFoundException("ServiceOffer by ID " + id + " not found"));
        BidMaterial newMat = new BidMaterial(serviceOffer, material, bidMaterial.getQuantity(), bidMaterial.getUnitPrice());
        BidMaterialId matId = new BidMaterialId(id, bidMaterial.getMaterial().getId());
        newMat.setId(matId);
        serviceOffer.addMaterialToServiceOffer(newMat);
        return serviceOfferRepo.save(serviceOffer);
    }
}
