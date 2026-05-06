package com.renovSolution.renov.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BidMaterialId implements Serializable {

    @Column(
            name ="service_offer_id"
    )
    private Long serviceOfferId;

    @Column(
            name ="material_id"
    )
    private Long materialId;

    public BidMaterialId() {
    }

    public BidMaterialId(Long serviceOfferId, Long materialId) {
        this.serviceOfferId = serviceOfferId;
        this.materialId = materialId;
    }

    public Long getServiceOfferId() {
        return serviceOfferId;
    }

    public void setServiceOfferId(Long serviceOfferId) {
        this.serviceOfferId = serviceOfferId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidMaterialId that = (BidMaterialId) o;
        return Objects.equals(serviceOfferId, that.serviceOfferId) && Objects.equals(materialId, that.materialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceOfferId, materialId);
    }
}
