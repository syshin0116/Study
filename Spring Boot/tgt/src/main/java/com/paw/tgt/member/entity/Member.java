package com.paw.tgt.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "MEMBERS")
public class Member {
    @Id
    @Column(name = "MEM_ID", nullable = false, length = 36)
    private String id;

    @Column(name = "MEM_PW", length = 15)
    private String memPw;

    @Column(name = "MEM_CALL", nullable = false, length = 20)
    private String memCall;

    @Column(name = "MEM_EMAIL", nullable = false, length = 100)
    private String memEmail;

    @Column(name = "MEM_ADDR")
    private String memAddr;

    @Column(name = "MEM_TYPE")
    private Boolean memType;

    @Column(name = "MEM_DOG_NAME", nullable = false, length = 50)
    private String memDogName;

    @Column(name = "MEM_DOG_NUM")
    private Long memDogNum;

    @Column(name = "MEM_BR_IDX")
    private Long memBrIdx;

    @Column(name = "MEM_BR_NAME", length = 30)
    private String memBrName;

    @Column(name = "MEM_DOG_WEIGHT", nullable = false)
    private Integer memDogWeight;

    @Column(name = "MEM_DOG_MBTI", length = 10)
    private String memDogMbti;

    @Column(name = "MEM_DOG_ETC")
    private String memDogEtc;

    @Column(name = "MEM_REG_DATE")
    private LocalDate memRegDate;

    @Column(name = "MEM_MOD_DATE")
    private LocalDate memModDate;

    @Column(name = "MEM_DEL_GB", length = 1)
    private String memDelGb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemPw() {
        return memPw;
    }

    public void setMemPw(String memPw) {
        this.memPw = memPw;
    }

    public String getMemCall() {
        return memCall;
    }

    public void setMemCall(String memCall) {
        this.memCall = memCall;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public String getMemAddr() {
        return memAddr;
    }

    public void setMemAddr(String memAddr) {
        this.memAddr = memAddr;
    }

    public Boolean getMemType() {
        return memType;
    }

    public void setMemType(Boolean memType) {
        this.memType = memType;
    }

    public String getMemDogName() {
        return memDogName;
    }

    public void setMemDogName(String memDogName) {
        this.memDogName = memDogName;
    }

    public Long getMemDogNum() {
        return memDogNum;
    }

    public void setMemDogNum(Long memDogNum) {
        this.memDogNum = memDogNum;
    }

    public Long getMemBrIdx() {
        return memBrIdx;
    }

    public void setMemBrIdx(Long memBrIdx) {
        this.memBrIdx = memBrIdx;
    }

    public String getMemBrName() {
        return memBrName;
    }

    public void setMemBrName(String memBrName) {
        this.memBrName = memBrName;
    }

    public Integer getMemDogWeight() {
        return memDogWeight;
    }

    public void setMemDogWeight(Integer memDogWeight) {
        this.memDogWeight = memDogWeight;
    }

    public String getMemDogMbti() {
        return memDogMbti;
    }

    public void setMemDogMbti(String memDogMbti) {
        this.memDogMbti = memDogMbti;
    }

    public String getMemDogEtc() {
        return memDogEtc;
    }

    public void setMemDogEtc(String memDogEtc) {
        this.memDogEtc = memDogEtc;
    }

    public LocalDate getMemRegDate() {
        return memRegDate;
    }

    public void setMemRegDate(LocalDate memRegDate) {
        this.memRegDate = memRegDate;
    }

    public LocalDate getMemModDate() {
        return memModDate;
    }

    public void setMemModDate(LocalDate memModDate) {
        this.memModDate = memModDate;
    }

    public String getMemDelGb() {
        return memDelGb;
    }

    public void setMemDelGb(String memDelGb) {
        this.memDelGb = memDelGb;
    }

}