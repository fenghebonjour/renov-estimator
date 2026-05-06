package com.renovSolution.renov.controller;

import com.renovSolution.renov.model.Labor;
import com.renovSolution.renov.service.LaborService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/labor")
public class LaborController {

    private final LaborService laborService;

    public LaborController(LaborService laborService) {
        this.laborService = laborService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Labor>> getAllLabors() {
        List<Labor> labors = laborService.findAllLabors();
        return new ResponseEntity<>(labors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Labor> getLaborById(@PathVariable("id") Long id) {
        Labor labor = laborService.findLaborById(id);
        return new ResponseEntity<>(labor, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Labor> addLabor(@RequestBody Labor labor) {
        Labor newLabor = laborService.addLabor(labor);
        return new ResponseEntity<>(newLabor, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Labor> updateLabor(@RequestBody Labor labor) {
        Labor updatedLabor = laborService.updateLabor(labor);
        return new ResponseEntity<>(updatedLabor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLabor(@PathVariable("id") Long id) {
        laborService.deleteLabor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
