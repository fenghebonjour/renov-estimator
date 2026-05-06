package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);

    List<User> findUsersByUsernameEqualsAndPasswordEquals(String username, String password);
}
