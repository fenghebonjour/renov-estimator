package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Address;
import com.renovSolution.renov.repo.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {

    private final AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public List<Address> findAllAddresses() {
        return addressRepo.findAll();
    }

    public Address findAddressById(Long id) {
        return addressRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Address by ID " + id + " not found"));
    }

    public Address updateAddress(Address address) {
        return addressRepo.save(address);
    }

    public Address addAddress(Address address) {
        return addressRepo.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepo.deleteAddressById(id);
    }
}
