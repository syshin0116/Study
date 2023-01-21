package com.paw.tgt.board.entity;

import com.paw.tgt.member.entity.Member;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    @Id @GeneratedValue
    @Column(name = "BC_IDX", nullable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BCC_IDX")
    @Column(name = "BC_BCC_IDX")
    private BoardCate categoryId;

    @Column(name = "BC_TITLE", length = 150)
    private String title;

    @Column(name = "BC_CONTENTS", length = 3000)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MEM_ID")
    @Column(name = "BC_WRITER_ID", length = 36)
    private Member writerId;

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
    public Board(Long id, BoardCate categoryId, String title, String content, Member writerId) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

}