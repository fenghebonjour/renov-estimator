package com.renovSolution.renov.service;


import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.ProjectBid;
import com.renovSolution.renov.repo.ProjectBidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectBidService {
    private final ProjectBidRepo projectBidRepo;

    @Autowired
    public ProjectBidService(ProjectBidRepo projectBidRepo) {
        this.projectBidRepo = projectBidRepo;
    }

    public List<ProjectBid> findAllProjectBids() {
        return projectBidRepo.findAll();
    }

    public ProjectBid findProjectBidById(Long id) {
        return projectBidRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Project Bid by ID " + id + " not found"));
    }

    public ProjectBid updateProjectBid(ProjectBid projectBid) {
        return projectBidRepo.save(projectBid);
    }

    public ProjectBid addProjectBid(ProjectBid projectBid) {
        return projectBidRepo.save(projectBid);
    }

    public void deleteProjectBid(Long id) {
        projectBidRepo.deleteProjectBidById(id);
    }

    public List<ProjectBid> findProjectBidsByClientId(Long id) {
        return projectBidRepo.findProjectBidsByClientId(id);
    }
}
