package com.paw.tgt.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOARD_COMM_CATE")
public class BoardCate {
    @Id
    @Column(name = "BCC_IDX", nullable = false)
    private Long id;

    @Column(name = "BCC_NAME", nullable = false, length = 30)
    private String bccName;
}