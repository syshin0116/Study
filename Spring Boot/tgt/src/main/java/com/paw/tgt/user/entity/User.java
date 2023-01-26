package com.paw.tgt.user.entity;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.dog.Dog;
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
    private Long idx;

    private String id;

    private String pw;

    private String phone;

    private String email;

    private String addr;

    private Boolean type;

    private LocalDate regDate;

    private LocalDate memModDate;

    //권한[ROLE_USER, ROLE_ADMIN]
    @Column(name="MEM_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy="user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Dog> dogs = new ArrayList<>();



}