package com.paw.tgt.board.entity;

import com.paw.tgt.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "BOARD_COMM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BC_IDX", nullable = false)
    private Long id;


    @Column(name = "BC_BCC_IDX")
    private Long cateNum;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BCC_IDX")
    private BoardCate boardCate;

    @Column(name = "BC_TITLE", length = 150)
    private String title;

    @Column(name = "BC_CONTENTS", length = 3000)
    private String content;

    @Column(name = "BC_WRITER_ID", length = 36)
    private Long writerId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MEM_ID")
    private User user;

//    @Column(name = "BC_WRITER_NAME", length = 50)
//    private String writerName;

    @Column(name = "BC_READHIT")
    @ColumnDefault("0")
    private Integer readHit;

    @CreatedDate
    @Column(name = "BC_REG_DATE", updatable = false)
    private LocalDate regDate;

    @LastModifiedDate
    @Column(name = "BC_MOD_DATE")
    private LocalDate modDate;

    @Column(name = "BC_DEL_GB", length = 1)
    private String delGb;

    @Builder
    public Board(Long id, Long cateNum, String title, String content, Long writerId) {
        this.id = id;
        this.cateNum = cateNum;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

}