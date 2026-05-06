package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="User")
@Table(name="app_user")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )

    @Column(
            name="user_id",
            nullable = false,
            updatable = false
    )
    protected Long id;

    @Column(
            name="username",
            nullable = true,
            columnDefinition = "TEXT"
    )
    protected String username;

    @Column(
            name="password",
            nullable = true,
            columnDefinition = "TEXT"
    )
    protected String password;

    @Column(
            name="registration_date",
            nullable = true,
            columnDefinition = "date"
    )
    protected LocalDate registrationDate;

    @Column(
            name="type",
            nullable = true,
            columnDefinition = "TEXT"
    )
    protected String type;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "user"
    )
    @JsonManagedReference(value="user-userAddress")
    protected List<UserAddress> addresses = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, LocalDate registrationDate, String type) {
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public void addAddress(UserAddress userAddress) {
        if (!addresses.contains(userAddress)) {
            addresses.add(userAddress);
        }
    }

    public void removeAddress(UserAddress userAddress) {
        if (addresses.contains(userAddress)) {
            addresses.remove(userAddress);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", type='" + type + '\'' +
                '}';
    }
}
