package com.renovSolution.renov.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAddressId implements Serializable {

    @Column(
            name ="user_id"
    )
    private Long userId;

    @Column(
            name ="address_id"
    )
    private Long addressId;

    public UserAddressId(Long userId, Long addressId) {
        this.userId = userId;
        this.addressId = addressId;
    }

    public UserAddressId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddressId that = (UserAddressId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, addressId);
    }

    @Override
    public String toString() {
        return "UserAddressId{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                '}';
    }
}
