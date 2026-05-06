package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
    void deleteAddressById(Long id);
}
