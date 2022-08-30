package com.my.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Component

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of= {"boardNo"})
@ToString
//@Data

@Entity
@Table(name="board_jpa")
@SequenceGenerator(name="boardjpa_seq_generator", 
				   sequenceName="board_jpa_seq",
				   initialValue=1,
				   allocationSize=1
		)
@DynamicInsert
@DynamicUpdate
public class Board {
	@Transient
	private int level; //글 레벨 1-원글, 2-답글, 3-답답글, 4-답답답글
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
					generator="boardjpa_seq_generator")
	@Column(name="board_no")
	private Long boardNo;
	
	@Column(name="board_parent_no")
	private Long boardParentNo;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@JsonFormat(pattern = "yy/mm/dd", timezone="Asia/Seoul")
	@Column(name="board_dt")
	@ColumnDefault(value="SYSDATE")
	private Date boardDt;
	
//	@NonNull //null로 설정(ex: setBoardId(null) 또는 new Board(~~~, null, ~~)되면 
//	//NullPointerException 예외를 일으켜 줍니다.
	@Column(name="board_id")
	private String boardId;
	
	@Column(name="board_viewcount")
	private int boardViewcount;
	
}
