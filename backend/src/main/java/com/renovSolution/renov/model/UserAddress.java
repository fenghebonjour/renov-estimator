package com.renovSolution.renov.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="UserAddress")
@Table(name="user_address")
public class UserAddress implements Serializable {

    @EmbeddedId
    private UserAddressId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name ="user_id",
            foreignKey = @ForeignKey(
                    name="user_address_user_fk"
            )
    )
    @JsonBackReference(value="user-userAddress")
    private User user;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(
            name ="address_id",
            foreignKey = @ForeignKey(
                    name="user_address_address_fk"
            )
    )
    private Address address;

    @Column(
            name="address_type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String addressType;

    public UserAddress() {
    }

    public UserAddress(UserAddressId id, User user, Address address, String addressType) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.addressType = addressType;
    }

    public UserAddress(User user, Address address, String addressType) {
        this.user = user;
        this.address = address;
        this.addressType = addressType;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public UserAddressId getId() {
        return id;
    }

    public void setId(UserAddressId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", user=" + user +
                ", address=" + address +
                ", addressType='" + addressType + '\'' +
                '}';
    }
}
