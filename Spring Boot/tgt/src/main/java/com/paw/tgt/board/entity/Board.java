package com.paw.tgt.board.entity;

import com.paw.tgt.user.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BOARD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "BC_IDX", nullable = false)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board")
    private BoardCate boardCate;

    private String title;

    private String content;

    @Column(name = "WRITER_ID", length = 36)
    private Long writerId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MEM_ID")
    private Users users;

//    @Column(name = "BC_WRITER_NAME", length = 50)
//    private String writerName;

    @Column(name = "BC_READHIT")
    @ColumnDefault("0")
    private Integer readHit;

    @CreatedDate
    private LocalDate regDate;

    @LastModifiedDate
    private LocalDate modDate;


    @Builder
    public Board(Long id, Long cateNum, String title, String content, Long writerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }

}