package com.paw.tgt.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOARD_COMM_CATE")
public class BoardCommCate {
    @Id
    @Column(name = "BCC_IDX", nullable = false)
    private Long id;

    @Column(name = "BCC_NAME", nullable = false, length = 30)
    private String bccName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBccName() {
        return bccName;
    }

    public void setBccName(String bccName) {
        this.bccName = bccName;
    }

}