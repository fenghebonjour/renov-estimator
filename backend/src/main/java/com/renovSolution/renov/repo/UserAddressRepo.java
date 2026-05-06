package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
}
