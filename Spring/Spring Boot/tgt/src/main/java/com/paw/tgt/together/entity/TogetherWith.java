package com.paw.tgt.together.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TOGETHER_WITH")
public class TogetherWith {
    @Id
    @Column(name = "TW_IDX", nullable = false)
    private Long id;

    @Column(name = "TW_TO_IDX")
    private Long twToIdx;

    @Column(name = "TW_MEM_ID", length = 36)
    private String twMemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTwToIdx() {
        return twToIdx;
    }

    public void setTwToIdx(Long twToIdx) {
        this.twToIdx = twToIdx;
    }

    public String getTwMemId() {
        return twMemId;
    }

    public void setTwMemId(String twMemId) {
        this.twMemId = twMemId;
    }

}