package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.ProjectBid;
import com.renovSolution.renov.service.ProjectBidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-bid")
public class ProjectBidController {

    private final ProjectBidService projectBidService;

    public ProjectBidController(ProjectBidService projectBidService) {
        this.projectBidService = projectBidService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectBid>> getAllProjectBids() {
        List<ProjectBid> bids = projectBidService.findAllProjectBids();
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProjectBid> getProjectBidById(@PathVariable("id") Long id) {
        ProjectBid bid = projectBidService.findProjectBidById(id);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectBid> addProjectBid(@RequestBody ProjectBid projectBid) {
        ProjectBid newBid = projectBidService.addProjectBid(projectBid);
        return new ResponseEntity<>(newBid, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectBid> updateProjectBid(@RequestBody ProjectBid projectBid) {
        ProjectBid updatedBid = projectBidService.updateProjectBid(projectBid);
        return new ResponseEntity<>(updatedBid, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectBid(@PathVariable("id") Long id) {
        projectBidService.deleteProjectBid(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<ProjectBid>> getProjectBidsByClientId(@PathVariable("id") Long id) {
        List<ProjectBid> bids = projectBidService.findProjectBidsByClientId(id);
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }
}
