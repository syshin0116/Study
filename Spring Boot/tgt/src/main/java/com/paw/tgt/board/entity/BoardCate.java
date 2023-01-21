package com.paw.tgt.board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BOARD_COMM_CATE")
public class BoardCate {
    @Id
    @GeneratedValue
    @Column(name = "BCC_IDX", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "boardCate")
    private Board board;

    @Column(name = "BCC_NAME", nullable = false, length = 30)
    private String bccName;
}