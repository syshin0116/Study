package com.paw.tgt.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "PLACE")
public class Place {
    @Id
    @Column(name = "PL_IDX", nullable = false)
    private Long id;

    @Column(name = "PL_NAME", nullable = false, length = 75)
    private String plName;

    @Column(name = "PL_CATE", length = 30)
    private String plCate;

    @Column(name = "PL_CALL", length = 20)
    private String plCall;

    @Column(name = "PL_LOC", nullable = false)
    private String plLoc;

    @Column(name = "PL_OFFDAY", length = 14)
    private String plOffday;

    @Column(name = "PL_OPEN", length = 4)
    private String plOpen;

    @Column(name = "PL_CLOSE", length = 4)
    private String plClose;

    @Column(name = "PL_MOD_DATE")
    private LocalDate plModDate;

    @Column(name = "PL_DEL_GB", length = 1)
    private String plDelGb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlName() {
        return plName;
    }

    public void setPlName(String plName) {
        this.plName = plName;
    }

    public String getPlCate() {
        return plCate;
    }

    public void setPlCate(String plCate) {
        this.plCate = plCate;
    }

    public String getPlCall() {
        return plCall;
    }

    public void setPlCall(String plCall) {
        this.plCall = plCall;
    }

    public String getPlLoc() {
        return plLoc;
    }

    public void setPlLoc(String plLoc) {
        this.plLoc = plLoc;
    }

    public String getPlOffday() {
        return plOffday;
    }

    public void setPlOffday(String plOffday) {
        this.plOffday = plOffday;
    }

    public String getPlOpen() {
        return plOpen;
    }

    public void setPlOpen(String plOpen) {
        this.plOpen = plOpen;
    }

    public String getPlClose() {
        return plClose;
    }

    public void setPlClose(String plClose) {
        this.plClose = plClose;
    }

    public LocalDate getPlModDate() {
        return plModDate;
    }

    public void setPlModDate(LocalDate plModDate) {
        this.plModDate = plModDate;
    }

    public String getPlDelGb() {
        return plDelGb;
    }

    public void setPlDelGb(String plDelGb) {
        this.plDelGb = plDelGb;
    }

}