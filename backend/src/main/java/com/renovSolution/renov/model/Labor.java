package com.renovSolution.renov.model;


import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="Labor")
@Table(name="labor")
public class Labor {

    @Id
    @SequenceGenerator(
            name="labor_sequence",
            sequenceName = "labor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "labor_sequence"
    )

    @Column(
            name="labor_id",
            nullable = false,
            updatable = false
    )
    protected Long id;

    @Column(
            name="description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name="grade",
            nullable = true
    )
    private int grade;

    @Column(
            name="hourly_rate",
            nullable = true
    )
    private double hourlyRate;

    public Labor() {
    }

    public Labor(String description, int grade, double hourlyRate) {
        this.description = description;
        this.grade = grade;
        this.hourlyRate = hourlyRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Long getId() {
        return id;
    }
}
