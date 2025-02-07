package com.kosta.finalproject.dto;
// dto와 entity역할 다 담당
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
@EqualsAndHashCode(of = {"boardNo"})
@ToString

// Data
@Entity
@Table(name = "CAR_BOARD")  // 테이블 선언 밑 이름 지정
@SequenceGenerator(name="car_board_number",
                   sequenceName="car_board_seq",
                   initialValue=1,
                   allocationSize=1)
@DynamicInsert
@DynamicUpdate
public class DriverBoard {
	@Transient // DB테이블에 컬럼으로 만들어질 변수가 아님(ORM에서 제외된다)
	private int level;  // 글 레벨 : 1-원글, 2-댓글, 3-대댓글
	
	@Id  // PK
	@GeneratedValue(strategy = GenerationType.SEQUENCE,  // DB의 시퀀스 컬럼을 이용
	                generator="car_board_generator")
	@Column(name="CARBOARDNUM")  // 시퀀스 값이 PK인게 맞나? 내가 잘못 안건가?
	private Long carBoardNo;  // 게시글 번호
	
	@Column(name="USERID")
	@NotNull
//	@ManyToOne  // FK로 연결
//	@JoinColumn(name="FK로 받을 PK가 있는 테이블?")
//	private "FK로 받을 PK가 있는 테이블" userId;
	private String userId;  // 게시자 아이디
	
	@Column(name="CARBOARDSTATUS")
	@NotNull
	private Long carBoardStatus;  // 게시글 상태(차구해요 0 / 친구해요 1)
	
	@Column(name="CARBOARDTITLE")
	@NotNull
	private String carBoardTitle;  // 제목
	
	@Column(name="STARTPOINT")
	@NotNull
	private String startPoint;  // 출발지

	@Column(name="ENDPOINT")
	@NotNull
	private String endPoint;  // 목적지
	
//	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asiz/Seoul")
	@Column(name="STARTTIME")
//	@ColumnDefault(value="SYSDATE")
	private Date startTime;  // 출발일시
	
	@Column(name="CARINFO")
	private String carInfo;  // 차 정보(차종, 연식)
	
	@Column(name="MAXSEAT")
	private Long maxSeat;  // 탑승가능인원

	@Column(name="USERLIKE")
	@ColumnDefault(value = "0")
	private Long userLike;  // 좋아요 수

	@Column(name="CARBOARDCONTENTS")
	private String carBoardContents;  // 글 내용
	
	// 이 밑은 우리가 입력하는거와는 별개로 게시판에 나타내기 위해
	// DB에 넣는거니까 아래쪽에 위치하도록 했다.
	@Column(name="PARENTNO")
	@ColumnDefault(value = "0")
	private Long boardParentNo; // 페이지 번호
	
	@Column(name="VIEWCOUNT")
	private Integer boardViewcount;  // 조회수
}
