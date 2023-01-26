package com.paw.tgt.user.entity;

import com.paw.tgt.board.entity.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MEM_PW", length = 15)
    private String pw;

    @Column(name = "MEM_CALL", nullable = false, length = 20)
    private String phone;

    @Column(name = "MEM_EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "MEM_ADDR")
    private String addr;

    @Column(name = "MEM_TYPE")
    private Boolean type;

    @Column(name = "MEM_DOG_NAME", nullable = false, length = 50)
    private String dogName;

    @Column(name = "MEM_DOG_NUM")
    private Long dogNum;

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

    //권한[ROLE_USER, ROLE_ADMIN]
    @Column(name="MEM_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy="writerId")
    private List<Board> boards = new ArrayList<>();



}