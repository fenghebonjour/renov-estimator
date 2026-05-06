package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity(name="Contractor")
@Table(name="contractor")
@Inheritance(strategy=InheritanceType.JOINED)
public class Contractor extends User implements Serializable {

    @Column(
            name="rating",
            nullable = false
    )
    private int rating;

    @Column(
            name="years_of_experience",
            nullable = false
    )
    private int yearsOfExperience;

    @Column(
            name="specialty",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String specialty;

    @Column(
            name="phone",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phone;

    @Column(
            name="email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @OneToMany(
            mappedBy = "contractor",
            orphanRemoval = true,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference(value="serviceOffer-contractor")
    private List<ServiceOffer> serviceOffers = new ArrayList<>();

    public Contractor() {
    }

    public Contractor(String username, String password, LocalDate registrationDate, String type, int rating,
                      int yearsOfExperience, String specialty, String phone, String email) {
        super(username, password, registrationDate, type);
        this.rating = rating;
        this.yearsOfExperience = yearsOfExperience;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ServiceOffer> getServiceOffers() {
        return serviceOffers;
    }

    public void addServiceOffer(ServiceOffer serviceOffer) {
        if (!serviceOffers.contains(serviceOffer)) {
            serviceOffers.add(serviceOffer);
            serviceOffer.setContractor(this);
        }
    }
}
