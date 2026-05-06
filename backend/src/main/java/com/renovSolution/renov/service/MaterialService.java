package com.renovSolution.renov.service;

import com.renovSolution.renov.exception.UserNotFoundException;
import com.renovSolution.renov.model.Material;
import com.renovSolution.renov.repo.MaterialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MaterialService {

    private final MaterialRepo materialRepo;

    @Autowired
    public MaterialService(MaterialRepo materialRepo) {
        this.materialRepo = materialRepo;
    }

    public List<Material> findAllMaterials() {
        return materialRepo.findAll();
    }

    public Material findMaterialById(Long id) {
        return materialRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Material by ID " + id + " not found"));
    }

    public Material updateMaterial(Material material) {
        return materialRepo.save(material);
    }

    public Material addMaterial(Material material) {
        return materialRepo.save(material);
    }

    public void deleteMaterial(Long id) {
        materialRepo.deleteMaterialById(id);
    }
}
