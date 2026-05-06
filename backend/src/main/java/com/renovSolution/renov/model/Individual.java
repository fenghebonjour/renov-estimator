package com.renovSolution.renov.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name="Individual")
@Table(name="individual")
public class Individual extends Contractor implements Serializable {

    @Column(
            name="last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name="first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name="certification",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String certification;

    public Individual() {
    }

    public Individual(String username,
                      String password,
                      LocalDate registrationDate,
                      String type,
                      int rating,
                      int yearsOfExperience,
                      String specialty,
                      String phone,
                      String email,
                      String lastName,
                      String firstName,
                      String certification) {
        super(username, password, registrationDate, type, rating, yearsOfExperience, specialty, phone, email);
        this.lastName = lastName;
        this.firstName = firstName;
        this.certification = certification;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }
}
