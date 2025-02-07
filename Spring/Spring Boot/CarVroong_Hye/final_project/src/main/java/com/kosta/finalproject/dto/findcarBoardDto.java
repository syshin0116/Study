package com.kosta.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class findcarBoardDto {
	private int findCarBoardNum;
	private String id;
	private String boardtitle;
	private String boardcontents;
	
	private String startpoint;
	private String endpoint;
	private String date;
	
	private int like;
}
