package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.Address;
import com.renovSolution.renov.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") Long id) {
        Address address = addressService.findAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        Address newAddress = addressService.addAddress(address);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
