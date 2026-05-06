package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="BidLabor")
@Table(name="bid_labor")
public class BidLabor {

    @JsonIgnore
    @EmbeddedId
    private BidLaborId id;

    @ManyToOne
    @MapsId("serviceOfferId")
    @JoinColumn(
            name ="service_offer_id",
            foreignKey = @ForeignKey(
                    name="bid_labor_service_offer_fk"
            )
    )
    @JsonBackReference(value="serviceOffer-laborItems")
    private ServiceOffer serviceOffer;

    @ManyToOne
    @MapsId("laborId")
    @JoinColumn(
            name ="labor_id",
            foreignKey = @ForeignKey(
                    name="bid_labor_labor_fk"
            )
    )
    private Labor labor;

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

    public BidLabor() {
    }

    public BidLabor(BidLaborId id, ServiceOffer serviceOffer, Labor labor, int quantity, double unitPrice) {
        this.id = id;
        this.serviceOffer = serviceOffer;
        this.labor = labor;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BidLaborId getId() {
        return id;
    }

    public void setId(BidLaborId id) {
        this.id = id;
    }

    public ServiceOffer getServiceOffer() {
        return serviceOffer;
    }

    public void setServiceOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
    }

    public Labor getLabor() {
        return labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
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
