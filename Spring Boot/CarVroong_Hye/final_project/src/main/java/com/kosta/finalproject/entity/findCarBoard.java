package com.kosta.finalproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//운전자를 구하는 게시판 테이블
@Entity
@Table(name="findCarBoard_tbl") //오라클에 있는테이블명과 맵핑해야하는
@Data
public class findCarBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int boardnum;
	
	@Column(nullable=false, length=50)
	private String id;
	
	@Column(nullable=false, length=50)
	private String boardtitle;
	
	@Column(nullable=false, length=500)
	private String boardcontents;
	
	@Column(nullable=false, length=50)
	private String startpoint;
	
	@Column(nullable=false, length=50)
	private String endpoint;
	
	@Column(nullable=false)
	private String date;
	
	@Column
	private int like;
}
