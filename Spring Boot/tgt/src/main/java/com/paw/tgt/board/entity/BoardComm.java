package com.paw.tgt.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "BOARD_COMM")
public class BoardComm {
    @Id
    @Column(name = "BC_IDX", nullable = false)
    private Long id;

    @Column(name = "BC_BCC_NAME", length = 30)
    private String bcBccName;

    @Column(name = "BC_TITLE", length = 150)
    private String bcTitle;

    @Column(name = "BC_CONTENTS", length = 3000)
    private String bcContents;

    @Column(name = "BC_WRITER_ID", length = 36)
    private String bcWriterId;

    @Column(name = "BC_WRITER_NAME", length = 50)
    private String bcWriterName;

    @Column(name = "BC_READHIT")
    private Long bcReadhit;

    @Column(name = "BC_REG_DATE")
    private LocalDate bcRegDate;

    @Column(name = "BC_MOD_DATE")
    private LocalDate bcModDate;

    @Column(name = "BC_DEL_GB", length = 1)
    private String bcDelGb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBcBccName() {
        return bcBccName;
    }

    public void setBcBccName(String bcBccName) {
        this.bcBccName = bcBccName;
    }

    public String getBcTitle() {
        return bcTitle;
    }

    public void setBcTitle(String bcTitle) {
        this.bcTitle = bcTitle;
    }

    public String getBcContents() {
        return bcContents;
    }

    public void setBcContents(String bcContents) {
        this.bcContents = bcContents;
    }

    public String getBcWriterId() {
        return bcWriterId;
    }

    public void setBcWriterId(String bcWriterId) {
        this.bcWriterId = bcWriterId;
    }

    public String getBcWriterName() {
        return bcWriterName;
    }

    public void setBcWriterName(String bcWriterName) {
        this.bcWriterName = bcWriterName;
    }

    public Long getBcReadhit() {
        return bcReadhit;
    }

    public void setBcReadhit(Long bcReadhit) {
        this.bcReadhit = bcReadhit;
    }

    public LocalDate getBcRegDate() {
        return bcRegDate;
    }

    public void setBcRegDate(LocalDate bcRegDate) {
        this.bcRegDate = bcRegDate;
    }

    public LocalDate getBcModDate() {
        return bcModDate;
    }

    public void setBcModDate(LocalDate bcModDate) {
        this.bcModDate = bcModDate;
    }

    public String getBcDelGb() {
        return bcDelGb;
    }

    public void setBcDelGb(String bcDelGb) {
        this.bcDelGb = bcDelGb;
    }

}