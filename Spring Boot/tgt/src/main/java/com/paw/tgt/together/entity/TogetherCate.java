package com.paw.tgt.together.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TOGETHER_CATE")
public class TogetherCate {
    @Id
    @Column(name = "TC_IDX", nullable = false)
    private Long id;

    @Column(name = "TC_NAME", nullable = false, length = 30)
    private String tcName;

    @Column(name = "TC_COUNT")
    private Long tcCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public Long getTcCount() {
        return tcCount;
    }

    public void setTcCount(Long tcCount) {
        this.tcCount = tcCount;
    }

}