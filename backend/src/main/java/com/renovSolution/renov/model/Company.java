package com.renovSolution.renov.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity(name="Company")
@Table(name="company")
public class Company extends Contractor implements Serializable {

    @Column(
            name="name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name="contact_person",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String contactPerson;

    public Company() {
    }

    public Company(
            String username,
            String password,
            LocalDate registrationDate,
            String type,
            int rating,
            int yearsOfExperience,
            String specialty,
            String phone,
            String email,
            String name,
            String contactPerson
    ) {
        super(username, password, registrationDate, type, rating, yearsOfExperience, specialty, phone, email);
        this.name = name;
        this.contactPerson = contactPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
