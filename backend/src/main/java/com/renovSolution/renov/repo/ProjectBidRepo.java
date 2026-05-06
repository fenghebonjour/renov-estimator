package com.renovSolution.renov.repo;

import com.renovSolution.renov.model.ProjectBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectBidRepo extends JpaRepository<ProjectBid, Long> {
    void deleteProjectBidById(Long id);

    List<ProjectBid> findProjectBidsByClientId(Long id);
}
