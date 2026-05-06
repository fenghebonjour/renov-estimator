package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.User;
import com.renovSolution.renov.model.UserAddress;
import com.renovSolution.renov.service.UserAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-address")
public class UserAddressController {

    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<User> addAddressToUser(@RequestBody UserAddress userAddress, @PathVariable("id") Long id) {
        User user = userAddressService.addAddressToUser(userAddress, id);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserAddress>> getAllUserAddresses() {
        List<UserAddress> userAddresses = userAddressService.findAllUserAddresses();
        return new ResponseEntity<>(userAddresses, HttpStatus.OK);
    }
}
