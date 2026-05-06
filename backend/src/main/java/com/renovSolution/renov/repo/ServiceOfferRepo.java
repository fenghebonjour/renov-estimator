package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Contractor;
import com.renovSolution.renov.model.ServiceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceOfferRepo extends JpaRepository<ServiceOffer, Long> {

    Optional<ServiceOffer> findServiceOfferById(Long id);

    List<ServiceOffer> findServiceOffersByContractor(Contractor contractor);

    List<ServiceOffer> findServiceOffersByContractorId(Long id);

    List<ServiceOffer> findServiceOffersByProjectBidId(Long id);

    void deleteServiceOfferById(Long id);
}
