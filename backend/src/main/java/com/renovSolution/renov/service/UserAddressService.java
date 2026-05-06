package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.*;
import com.renovSolution.renov.repo.AddressRepo;
import com.renovSolution.renov.repo.UserAddressRepo;
import com.renovSolution.renov.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAddressService {

    private final UserRepo userRepo;
    private final UserAddressRepo userAddressRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public UserAddressService(UserRepo userRepo,
                               UserAddressRepo userAddressRepo,
                               AddressRepo addressRepo) {
        this.userRepo = userRepo;
        this.userAddressRepo = userAddressRepo;
        this.addressRepo = addressRepo;
    }

    public User addAddressToUser(UserAddress userAddress, Long id) {
        Address adr = new Address(
                userAddress.getAddress().getStreetNumber(),
                userAddress.getAddress().getUnit(),
                userAddress.getAddress().getStreet(),
                userAddress.getAddress().getCity(),
                userAddress.getAddress().getProvince(),
                userAddress.getAddress().getCountry(),
                userAddress.getAddress().getPostalCode()
        );
        Address newAdr = addressRepo.save(adr);
        User user = userRepo.findUserById(id).orElseThrow(() -> new UserNotFoundException("User by ID " + id + " not found"));
        UserAddressId userAdrId = new UserAddressId(id, newAdr.getId());
        UserAddress newUserAddress = new UserAddress(userAdrId, user, newAdr, userAddress.getAddressType());
        newUserAddress.setId(userAdrId);
        user.addAddress(newUserAddress);
        return userRepo.save(user);
    }

    public List<UserAddress> findAllUserAddresses() {
        return userAddressRepo.findAll();
    }
}
