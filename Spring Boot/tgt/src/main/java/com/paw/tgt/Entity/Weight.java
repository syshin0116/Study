package com.paw.tgt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WEIGHT")
public class Weight {
    @Id
    @Column(name = "WT_IDX", nullable = false)
    private Long id;

    @Column(name = "WT_START", nullable = false)
    private Integer wtStart;

    @Column(name = "WT_END", nullable = false)
    private Integer wtEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWtStart() {
        return wtStart;
    }

    public void setWtStart(Integer wtStart) {
        this.wtStart = wtStart;
    }

    public Integer getWtEnd() {
        return wtEnd;
    }

    public void setWtEnd(Integer wtEnd) {
        this.wtEnd = wtEnd;
    }

}