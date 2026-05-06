package com.renovSolution.renov.service;

import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.User;
import com.renovSolution.renov.repo.AddressRepo;
import com.renovSolution.renov.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public UserService(UserRepo userRepo, AddressRepo addressRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.findUserById(id).orElseThrow(() -> new UserNotFoundException("User by ID " + id + " not found"));
    }

    public void deleteUser(Long id) {
        userRepo.deleteUserById(id);
    }

    public List<User> findUsersByUsernameAndPassword(String username, String password) {
        return userRepo.findUsersByUsernameEqualsAndPasswordEquals(username, password);
    }
}
