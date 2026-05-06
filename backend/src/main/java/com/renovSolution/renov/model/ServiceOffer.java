package com.renovSolution.renov.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="ServiceOffer")
@Table(name="service_offer")
public class ServiceOffer {

    @Id
    @SequenceGenerator(
            name="service_offer_sequence",
            sequenceName = "service_offer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "service_offer_sequence"
    )

    @Column(
            name="service_offer_id",
            nullable = false,
            updatable = false
    )
    protected Long id;

    @Column(
            name="offer_date",
            nullable = true,
            columnDefinition = "date"
    )
    protected LocalDate offerDate;

    @Column(
            name="valid_until",
            nullable = true,
            columnDefinition = "date"
    )
    protected LocalDate validUntil;

    @Column(
            name="status",
            nullable = true,
            columnDefinition = "TEXT"
    )
    protected String status;

    @Column(
            name="amount",
            nullable = true
    )
    private double amount;

    @ManyToOne
    @JoinColumn(
            name="project_bid_id",
            referencedColumnName = "project_bid_id",
            foreignKey = @ForeignKey(
                    name="service_offer_project_bid_fk"
            )
    )
    @JsonBackReference(value="projectBid-serviceOffer")
    private ProjectBid projectBid;

    @ManyToOne
    @JoinColumn(
            name="user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(
                    name="service_offer_contractor_fk"
            )
    )
    @JsonBackReference(value="serviceOffer-contractor")
    private Contractor contractor;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "serviceOffer"
    )
    @JsonManagedReference(value="serviceOffer-materials")
    protected List<BidMaterial> materials = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "serviceOffer"
    )
    @JsonManagedReference(value="serviceOffer-laborItems")
    protected List<BidLabor> laborItems = new ArrayList<>();

    public ServiceOffer() {
    }

    public ServiceOffer(LocalDate offerDate, LocalDate validUntil, String status, double amount) {
        this.offerDate = offerDate;
        this.validUntil = validUntil;
        this.status = status;
        this.amount = amount;
    }

    public LocalDate getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDate offerDate) {
        this.offerDate = offerDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public ProjectBid getProjectBid() {
        return projectBid;
    }

    public void setProjectBid(ProjectBid projectBid) {
        this.projectBid = projectBid;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public List<BidMaterial> getMaterials() {
        return materials;
    }

    public List<BidLabor> getLaborItems() {
        return laborItems;
    }

    public void addMaterialToServiceOffer(BidMaterial bidMaterial) {
        if (!materials.contains(bidMaterial)) {
            materials.add(bidMaterial);
        }
    }

    public void addLaborToServiceOffer(BidLabor bidLabor) {
        if (!laborItems.contains(bidLabor)) {
            laborItems.add(bidLabor);
        }
    }
}
