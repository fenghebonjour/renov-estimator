package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.Individual;
import com.renovSolution.renov.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/individual")
public class IndividualController {

    private final IndividualService individualService;

    public IndividualController(IndividualService individualService) {
        this.individualService = individualService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Individual>> getAllIndividuals() {
        List<Individual> individuals = individualService.findAllIndividuals();
        return new ResponseEntity<>(individuals, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Individual> getIndividualById(@PathVariable("id") Long id) {
        Individual individual = individualService.findIndividualById(id);
        return new ResponseEntity<>(individual, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Individual> addIndividual(@RequestBody Individual individual) {
        Individual newIndividual = individualService.addIndividual(individual);
        return new ResponseEntity<>(newIndividual, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Individual> updateIndividual(@RequestBody Individual individual) {
        Individual updatedIndividual = individualService.updateIndividual(individual);
        return new ResponseEntity<>(updatedIndividual, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIndividual(@PathVariable("id") Long id) {
        individualService.deleteIndividual(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
