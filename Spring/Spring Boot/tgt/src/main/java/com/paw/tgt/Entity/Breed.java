package com.paw.tgt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BREED")
public class Breed {
    @Id
    @Column(name = "BR_IDX", nullable = false)
    private Long id;

    @Column(name = "BR_NAME", nullable = false, length = 30)
    private String brName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

}