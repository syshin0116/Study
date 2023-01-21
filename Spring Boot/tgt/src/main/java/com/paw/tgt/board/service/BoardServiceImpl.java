package com.paw.tgt.board.service;

import java.util.List;
import java.util.Map;


import com.paw.tgt.board.entity.Board;
import com.paw.tgt.board.entity.BoardCate;
import com.paw.tgt.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public List<Board> boardListByBoardCate(BoardCate boardCate, Pageable pageable){
		return boardRepository.findAllByBoardCate(boardCate, pageable);
	}

	// 멍멍왈왈 게시판 리스트 띄우기
//	@Override
//	public List<Map<String, Object>> boardList(Map<String, Object> map) throws Exception {
//		return boardRepository.findAll(map);
//	}
//
//	//멍멍왈왈 게시판 글쓰기
//	@Override
//	public void boardWrite(Map<String, Object> map) throws Exception {
//		boardRepository.boardWrite(map);
//	}
//
//	//멍멍왈왈 디테일
//	@Override
//	public Map<String, Object> boardDetail(Map<String, Object> map) throws Exception {
//		boardRepository.boardHit(map);
//		Map<String, Object> resultMap = boardRepository.boardDetail(map);
//		return resultMap;
//	}
//
//	//멍멍왈왈 수정하기
//	@Override
//	public void boardModify(Map<String ,Object> map) throws Exception {
//		boardRepository.boardModify(map);
//	}
//
//	//멍멍왈왈 글 삭제하기
//	@Override
//	public void boardDelete(Map<String ,Object> map) throws Exception {
//		boardRepository.boardDelete(map);
//	}
//
//
	
}
