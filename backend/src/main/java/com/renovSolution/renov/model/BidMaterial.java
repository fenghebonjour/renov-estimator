package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity(name="BidMaterial")
@Table(name="bid_material")
public class BidMaterial {

    @JsonIgnore
    @EmbeddedId
    private BidMaterialId id;

    @ManyToOne
    @MapsId("serviceOfferId")
    @JoinColumn(
            name ="service_offer_id",
            foreignKey = @ForeignKey(
                    name="bid_material_service_offer_fk"
            )
    )
    @JsonBackReference(value="serviceOffer-materials")
    private ServiceOffer serviceOffer;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(
            name ="material_id",
            foreignKey = @ForeignKey(
                    name="bid_material_material_fk"
            )
    )
    private Material material;

    @Column(
            name="quantity",
            nullable = true
    )
    private int quantity;

    @Column(
            name="unit_price",
            nullable = true
    )
    private double unitPrice;

    public BidMaterial() {
    }

    public BidMaterial(BidMaterialId id, ServiceOffer serviceOffer, Material material, int quantity, double unitPrice) {
        this.id = id;
        this.serviceOffer = serviceOffer;
        this.material = material;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BidMaterial(ServiceOffer serviceOffer, Material material, int quantity, double unitPrice) {
        this.serviceOffer = serviceOffer;
        this.material = material;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BidMaterialId getId() {
        return id;
    }

    public void setId(BidMaterialId id) {
        this.id = id;
    }

    public ServiceOffer getServiceOffer() {
        return serviceOffer;
    }

    public void setServiceOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
