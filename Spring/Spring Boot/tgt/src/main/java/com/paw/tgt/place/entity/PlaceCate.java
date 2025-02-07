package com.paw.tgt.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLACE_CATE")
public class PlaceCate {
    @Id
    @Column(name = "PC_IDX", nullable = false)
    private Long id;

    @Column(name = "PC_NAME", nullable = false, length = 30)
    private String pcName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

}