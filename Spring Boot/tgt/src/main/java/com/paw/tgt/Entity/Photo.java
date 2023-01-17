package com.paw.tgt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "PHOTO")
public class Photo {
    @Id
    @Column(name = "PH_IDX", nullable = false)
    private Long id;

    @Column(name = "PH_BOARD_IDX")
    private Long phBoardIdx;

    @Column(name = "PH_MEM_ID", length = 36)
    private String phMemId;

    @Column(name = "PH_BOARD_TYPE", nullable = false, length = 30)
    private String phBoardType;

    @Column(name = "PH_ORIGINAL_FILE_NAME", nullable = false, length = 50)
    private String phOriginalFileName;

    @Column(name = "PH_STORED_FILE_NAME", nullable = false, length = 50)
    private String phStoredFileName;

    @Column(name = "PH_FILE_SIZE", nullable = false)
    private Long phFileSize;

    @Column(name = "PH_REG_DATE")
    private LocalDate phRegDate;

    @Column(name = "PH_DEL_GB", length = 1)
    private String phDelGb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhBoardIdx() {
        return phBoardIdx;
    }

    public void setPhBoardIdx(Long phBoardIdx) {
        this.phBoardIdx = phBoardIdx;
    }

    public String getPhMemId() {
        return phMemId;
    }

    public void setPhMemId(String phMemId) {
        this.phMemId = phMemId;
    }

    public String getPhBoardType() {
        return phBoardType;
    }

    public void setPhBoardType(String phBoardType) {
        this.phBoardType = phBoardType;
    }

    public String getPhOriginalFileName() {
        return phOriginalFileName;
    }

    public void setPhOriginalFileName(String phOriginalFileName) {
        this.phOriginalFileName = phOriginalFileName;
    }

    public String getPhStoredFileName() {
        return phStoredFileName;
    }

    public void setPhStoredFileName(String phStoredFileName) {
        this.phStoredFileName = phStoredFileName;
    }

    public Long getPhFileSize() {
        return phFileSize;
    }

    public void setPhFileSize(Long phFileSize) {
        this.phFileSize = phFileSize;
    }

    public LocalDate getPhRegDate() {
        return phRegDate;
    }

    public void setPhRegDate(LocalDate phRegDate) {
        this.phRegDate = phRegDate;
    }

    public String getPhDelGb() {
        return phDelGb;
    }

    public void setPhDelGb(String phDelGb) {
        this.phDelGb = phDelGb;
    }

}