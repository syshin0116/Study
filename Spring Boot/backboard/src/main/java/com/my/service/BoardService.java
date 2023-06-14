package com.my.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.dto.Board;
import com.my.dto.PageBean;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.repository.BoardRepository;

@Service
public class BoardService {
	private static final int CNT_PER_PAGE = 3; // 페이지별 보여줄 목록수
	@Autowired
	private BoardRepository repository;

	/**
	 * 페이지별 게시글 목록과 페이지그룹정보를 반환한다
	 * 
	 * @param currentPage 검색할 페이지
	 * @return
	 * @throws FindException
	 */
	public PageBean<Board> boardList(int currentPage) throws FindException {

//		List<Board> list = repository.selectByPage(currentPage, CNT_PER_PAGE);
//		int totalCnt = repository.selectCount();// 총 행수 12, 13
//
//		int cntPerPageGroup = 2;// 페이지별 보여줄 페이지수
//
//		PageBean<Board> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
//
//		return pb1;
		int endRow = currentPage * CNT_PER_PAGE;
		int startRow =  endRow - CNT_PER_PAGE + 1;
		List<Board>list = repository.findByPage(startRow, endRow);
		long totalCnt = repository.count();
		int cntPerPageGroup = 2;//페이지별 보여줄 페이지수
		PageBean<Board> pb = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
		
		return pb;
	}

	/**
	 * 검색어를 이용한 게시글 검색 목록과 페이지 그룹정보를 반환한다
	 * 
	 * @param word        검색어
	 * @param currentPage 검색할 페이지
	 * @return
	 * @throws FindException
	 */
	public PageBean<Board> searchBoard(String word, int currentPage) throws FindException {
//		List<Board> list = repository.selectByWord(word, currentPage, CNT_PER_PAGE);
//		int totalCnt = repository.selectCount(word);
//		int cntPerPageGroup = 2;
//		PageBean<Board> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
//		return pb1;
		
		return null;
	}

	/**
	 * @param boardNo
	 * @return
	 * @throws FindException
	 */
	public Board viewBoard(Long boardNo) throws FindException {

		// 조회수를 1증가한다
//			Board b = new Board();
//			b.setBoardNo(boardNo);
//			b.setBoardViewcount(-1);
		//repository.update(b);
		Optional<Board> optB = repository.findById(boardNo);
		if(optB.isPresent()) { //조회수 1 업데이트 하는 방법
			Board b = optB.get();
			b.setBoardViewcount(b.getBoardViewcount()+1);
			repository.save(b);
		}else {
			throw new FindException("게시글이 없습니다");
		}
		
		// 게시글번호의 게시글 조회한다
		//Board b1 = repository.selectByBoardNo(boardNo);
		Optional<Board> optB1 = repository.findById(boardNo);
		if(optB1.isPresent()) {
			Board b1 = optB1.get();
			return b1;
		}
		else {
			throw new FindException("게시글이 없습니다");
		}
	}
	/**
	 * 글쓰기
	 * @param board
	 * @throws AddException
	 */
	public void writeBoard(Board board) throws AddException{
		board.setBoardParentNo(0L);
//		repository.insert(board);
		repository.save(board);
	}
	
	/**
	 * 답글쓰기
	 * @param board
	 * @throws AddException
	 */
	public void replyBoard(Board board) throws AddException{
		if(board.getBoardParentNo() == 0L) {
			throw new AddException("답글쓰기의 부모글번호가 없습니다");
		}
		repository.save(board);
	}

	/**
	 * @param b
	 * @throws ModifyException
	 */
	public void modifyBoard(Board board) throws ModifyException {
		Optional<Board> optB = repository.findById(board.getBoardNo());
		if(!optB.isPresent()) {
			throw new ModifyException("글이 없습니다");
		}
		repository.save(board);
	}
	
	//할일 조회수 1 증가 완성, 글삭제 완성
	public void removeBoard(Long boardNo) throws RemoveException {
		Optional<Board> optB = repository.findById(boardNo);
		if(optB.isPresent()) { //조회수 1 업데이트 하는 방법
			Board b = optB.get();
			//b.setBoardViewcount(b.getBoardViewcount()+1);
			repository.delete(b);
		}else {
			throw new RemoveException("게시글이 없습니다");
		}
	}


}