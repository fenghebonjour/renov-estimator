package com.renovSolution.renov.model;


import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="Material")
@Table(name="material")
public class Material implements Serializable {

    @Id
    @SequenceGenerator(
            name="material_sequence",
            sequenceName = "material_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "material_sequence"
    )

    @Column(
            name="material_id",
            nullable = false,
            updatable = false
    )
    protected Long id;

    @Column(
            name="description",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name="unit_price",
            nullable = true
    )
    private double unitPrice;

    public Material() {
    }

    public Material(String description, double unitPrice) {
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
