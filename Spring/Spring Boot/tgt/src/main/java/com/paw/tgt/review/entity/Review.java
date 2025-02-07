package com.paw.tgt.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "REVIEW")
public class Review {
    @Id
    @Column(name = "RE_IDX", nullable = false)
    private Long id;

    @Column(name = "RE_PL_IDX")
    private Long rePlIdx;

    @Column(name = "RE_WRITER_ID", length = 36)
    private String reWriterId;

    @Column(name = "RE_WRITER_NAME", length = 50)
    private String reWriterName;

    @Column(name = "RE_STAR", nullable = false)
    private Boolean reStar = false;

    @Column(name = "RE_PH_IDX")
    private Long rePhIdx;

    @Column(name = "RE_CONTENTS", nullable = false, length = 3000)
    private String reContents;

    @Column(name = "RE_REG_DATE")
    private LocalDate reRegDate;

    @Column(name = "RE_MOD_DATE")
    private LocalDate reModDate;

    @Column(name = "RE_DEL_GB", length = 1)
    private String reDelGb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRePlIdx() {
        return rePlIdx;
    }

    public void setRePlIdx(Long rePlIdx) {
        this.rePlIdx = rePlIdx;
    }

    public String getReWriterId() {
        return reWriterId;
    }

    public void setReWriterId(String reWriterId) {
        this.reWriterId = reWriterId;
    }

    public String getReWriterName() {
        return reWriterName;
    }

    public void setReWriterName(String reWriterName) {
        this.reWriterName = reWriterName;
    }

    public Boolean getReStar() {
        return reStar;
    }

    public void setReStar(Boolean reStar) {
        this.reStar = reStar;
    }

    public Long getRePhIdx() {
        return rePhIdx;
    }

    public void setRePhIdx(Long rePhIdx) {
        this.rePhIdx = rePhIdx;
    }

    public String getReContents() {
        return reContents;
    }

    public void setReContents(String reContents) {
        this.reContents = reContents;
    }

    public LocalDate getReRegDate() {
        return reRegDate;
    }

    public void setReRegDate(LocalDate reRegDate) {
        this.reRegDate = reRegDate;
    }

    public LocalDate getReModDate() {
        return reModDate;
    }

    public void setReModDate(LocalDate reModDate) {
        this.reModDate = reModDate;
    }

    public String getReDelGb() {
        return reDelGb;
    }

    public void setReDelGb(String reDelGb) {
        this.reDelGb = reDelGb;
    }

}