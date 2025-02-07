package com.paw.tgt.together.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "TOGETHER")
public class Together {
    @Id
    @Column(name = "TO_IDX", nullable = false)
    private Long id;

    @Column(name = "TO_TITLE", nullable = false, length = 150)
    private String toTitle;

    @Column(name = "TO_TC_NAME", length = 30)
    private String toTcName;

    @Column(name = "TO_CONTENTS", nullable = false, length = 3000)
    private String toContents;

    @Column(name = "TO_WRITER_ID", length = 36)
    private String toWriterId;

    @Column(name = "TO_WRITER_NAME", length = 50)
    private String toWriterName;

    @Column(name = "TO_DATE")
    private LocalDate toDate;

    @Column(name = "TO_TIME", length = 20)
    private String toTime;

    @Column(name = "TO_PEOPLE", nullable = false)
    private Integer toPeople;

    @Column(name = "TO_JOIN_PEOPLE")
    private Integer toJoinPeople;

    @Column(name = "TO_LOC", nullable = false)
    private String toLoc;

    @Column(name = "TO_BR_NAME", length = 30)
    private String toBrName;

    @Column(name = "TO_WT_IDX")
    private Long toWtIdx;

    @Column(name = "TO_REG_DATE")
    private LocalDate toRegDate;

    @Column(name = "TO_MOD_DATE")
    private LocalDate toModDate;

    @Column(name = "TO_DEL_GB", length = 1)
    private String toDelGb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToTitle() {
        return toTitle;
    }

    public void setToTitle(String toTitle) {
        this.toTitle = toTitle;
    }

    public String getToTcName() {
        return toTcName;
    }

    public void setToTcName(String toTcName) {
        this.toTcName = toTcName;
    }

    public String getToContents() {
        return toContents;
    }

    public void setToContents(String toContents) {
        this.toContents = toContents;
    }

    public String getToWriterId() {
        return toWriterId;
    }

    public void setToWriterId(String toWriterId) {
        this.toWriterId = toWriterId;
    }

    public String getToWriterName() {
        return toWriterName;
    }

    public void setToWriterName(String toWriterName) {
        this.toWriterName = toWriterName;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Integer getToPeople() {
        return toPeople;
    }

    public void setToPeople(Integer toPeople) {
        this.toPeople = toPeople;
    }

    public Integer getToJoinPeople() {
        return toJoinPeople;
    }

    public void setToJoinPeople(Integer toJoinPeople) {
        this.toJoinPeople = toJoinPeople;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getToBrName() {
        return toBrName;
    }

    public void setToBrName(String toBrName) {
        this.toBrName = toBrName;
    }

    public Long getToWtIdx() {
        return toWtIdx;
    }

    public void setToWtIdx(Long toWtIdx) {
        this.toWtIdx = toWtIdx;
    }

    public LocalDate getToRegDate() {
        return toRegDate;
    }

    public void setToRegDate(LocalDate toRegDate) {
        this.toRegDate = toRegDate;
    }

    public LocalDate getToModDate() {
        return toModDate;
    }

    public void setToModDate(LocalDate toModDate) {
        this.toModDate = toModDate;
    }

    public String getToDelGb() {
        return toDelGb;
    }

    public void setToDelGb(String toDelGb) {
        this.toDelGb = toDelGb;
    }

}