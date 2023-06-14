package kr.co.board.vo;

import lombok.Data;

/*Vo란 무엇인지 구글검색*/
@Data
public class BoardVo {
	
	/*lombok은 아직 설정안함*/

	/*테이블 프라이머리 키 (유일값) */
	private String tbSeq;
	/*게시판 제목*/
	private String tbTitle;
	/*게시판 내용*/
	private String tbContent;
	/*게시판 등록일*/
	private String tbRegDt;
	/*게시판 작성자*/
	private String tbRegId;
	
}
