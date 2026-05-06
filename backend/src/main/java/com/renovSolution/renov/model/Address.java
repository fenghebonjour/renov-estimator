package com.renovSolution.renov.model;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="Address")
@Table(name="address")
public class Address implements Serializable {

    @Id
    @SequenceGenerator(
            name="address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "address_sequence"
    )

    @Column(
            name="address_id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name="street_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String streetNumber;

    @Column(
            name="unit",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String unit;

    @Column(
            name="street",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String street;

    @Column(
            name="city",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String city;

    @Column(
            name="province",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String province;

    @Column(
            name="country",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String country;

    @Column(
            name="postal_code",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String postalCode;

    public Address() {
    }

    public Address(String streetNumber, String unit, String street, String city,
                   String province, String country, String postalCode) {
        this.streetNumber = streetNumber;
        this.unit = unit;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetNumber='" + streetNumber + '\'' +
                ", unit='" + unit + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
