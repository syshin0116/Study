package com.paw.tgt.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLACE_MENU")
public class PlaceMenu {
    @Id
    @Column(name = "PM_IDX", nullable = false)
    private Long id;

    @Column(name = "PM_PL_IDX")
    private Long pmPlIdx;

    @Column(name = "PM_NAME", nullable = false, length = 30)
    private String pmName;

    @Column(name = "PM_PRICE", nullable = false)
    private Long pmPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPmPlIdx() {
        return pmPlIdx;
    }

    public void setPmPlIdx(Long pmPlIdx) {
        this.pmPlIdx = pmPlIdx;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public Long getPmPrice() {
        return pmPrice;
    }

    public void setPmPrice(Long pmPrice) {
        this.pmPrice = pmPrice;
    }

}