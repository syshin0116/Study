package com.paw.tgt.member.entity;

import com.paw.tgt.board.entity.Board;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBERS")
public class Member {
    @Id
    @Column(name = "MEM_ID", nullable = false, length = 36)
    private Long id;

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

    //권한[ROLE_USER, ROLE_ADMIN]
    @Column(name="MEM_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy="writerId")
    private List<Board> boards = new ArrayList<>();



}