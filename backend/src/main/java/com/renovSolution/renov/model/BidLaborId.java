package com.renovSolution.renov.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BidLaborId implements Serializable {

    @Column(
            name ="service_offer_id"
    )
    private Long serviceOfferId;

    @Column(
            name ="labor_id"
    )
    private Long laborId;

    public BidLaborId() {
    }

    public BidLaborId(Long serviceOfferId, Long laborId) {
        this.serviceOfferId = serviceOfferId;
        this.laborId = laborId;
    }

    public Long getServiceOfferId() {
        return serviceOfferId;
    }

    public void setServiceOfferId(Long serviceOfferId) {
        this.serviceOfferId = serviceOfferId;
    }

    public Long getLaborId() {
        return laborId;
    }

    public void setLaborId(Long laborId) {
        this.laborId = laborId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidLaborId that = (BidLaborId) o;
        return Objects.equals(serviceOfferId, that.serviceOfferId) && Objects.equals(laborId, that.laborId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceOfferId, laborId);
    }
}
