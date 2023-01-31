package com.paw.tgt.board.service;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.board.entity.BoardCate;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService1 {
	//게시판별 리스트
	List<Board> boardListByBoardCate(BoardCate boardCate, Pageable pageable);
//	//멍멍왈왈 게시판 리스트 띄우기
//	List<Map<String, Object>> boardList(Map<String, Object> map) throws Exception;
//
//	//멍멍왈왈 게시판 글쓰기
//	void boardWrite(Map<String, Object> map) throws Exception;
//
//	//멍멍왈왈 디테일
//	Map<String, Object> boardDetail(Map<String, Object> map) throws Exception;
//
//	//멍멍왈왈 수정하기
//	void boardModify(Map<String, Object> map) throws Exception;
//
//	//멍멍왈왈 글 삭제하기
//	void boardDelete(Map<String, Object> map) throws Exception;
//
}
