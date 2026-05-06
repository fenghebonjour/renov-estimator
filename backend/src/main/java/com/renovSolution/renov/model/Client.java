package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity(name="Client")
@Table(name="client")
public class Client extends User implements Serializable {

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
            name="email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name="phone",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String phone;

    @OneToMany(
            mappedBy = "client",
            orphanRemoval = true,
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference(value="client-projectBid")
    private List<ProjectBid> projectBids = new ArrayList<>();

    public Client() {
    }

    public Client(
            String username,
            String password,
            LocalDate registrationDate,
            String type,
            String lastName,
            String firstName,
            String email,
            String phone
    ) {
        super(username, password, registrationDate, type);
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ProjectBid> getProjectBids() {
        return projectBids;
    }

    public void addProjectBid(ProjectBid projectBid) {
        if (!projectBids.contains(projectBid)) {
            projectBids.add(projectBid);
            projectBid.setClient(this);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", type='" + type + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
