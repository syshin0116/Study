package com.kosta.finalproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.finalproject.dto.DriverBoard;
import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.exception.AddException;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.exception.ModifyException;
import com.kosta.finalproject.repository.DriverBoardRepository;
@Service
public class DriverBoardService {
	private static final int CNT_PER_PAGE = 10; //페이지별 보여줄 목록수
	@Autowired
	private DriverBoardRepository repository;
	/**
	 * 페이지별 게시글 목록과 페이지그룹정보를 반환한다 
	 * @param currentPage 검색할 페이지
	 * @return 
	 * @throws FindException
	 */
	public PageBean<DriverBoard> boardList(int currentPage) throws FindException{
		int endRow = currentPage*CNT_PER_PAGE;
		int startRow = endRow - CNT_PER_PAGE + 1;
		List<DriverBoard> list =repository.findByPage(startRow, endRow);
		Long totalCnt = repository.count();    // 총 행수 받아오기
		int cntPerPageGroup = 2;  // 페이지별 보여줄 페이지수
		PageBean<DriverBoard> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
		return pb1;
	}

	/**
	 * 검색어를 이용한 게시글 검색 목록과 페이지 그룹정보를 반환한다
	 * @param word 검색어
	 * @param currentPage 검색할 페이지
	 * @return
	 * @throws FindException
	 */
	public PageBean<DriverBoard> searchBoard(String word, int currentPage) throws FindException{
//		List<DriverBoard> list = repository.selectByWord(word, currentPage, CNT_PER_PAGE);
//		Long totalCnt = repository.selectCount(word);
//		int cntPerPageGroup = 2;
//		PageBean<Board> pb1 = new PageBean<>(list, totalCnt, currentPage, cntPerPageGroup, CNT_PER_PAGE);
//		return pb1;
		return null;
	}
	/**
	 * 게시글번호의 조회수를 1증가한다
	 * 게시글번호의 게시글을 반환한다
	 * @param boardNo 게시글번호
	 * @return
	 * @throws FindException
	 */
	public DriverBoard viewBoard(Long carBoardNo) throws FindException{
		//조회수를 1증가한다
		Optional<DriverBoard> optB = repository.findById(carBoardNo);
		if(optB.isPresent()) {  // optB가 존재하는 경우에는
			DriverBoard b = optB.get();
			b.setBoardViewcount(b.getBoardViewcount()+1);
			repository.save(b);
		} else {
			throw new FindException("게시글이 없습니다");
		}
		
		//게시글번호의 게시글 조회한다
		//Board b1 = repository.selectByBoardNo(boardNo);
		Optional<DriverBoard> optB1 = repository.findById(carBoardNo);
		if(optB1.isPresent()) {
			DriverBoard b1= optB1.get();
			return b1;
		} else {
			throw new FindException("게시글이 없습니다");
		}
	}
	/**
	 * 글쓰기
	 * @param board
	 * @throws AddException
	 */
	public void writeBoard(DriverBoard board) throws AddException{
		board.setBoardParentNo(0L);
//		repository.insert(board);
		repository.save(board);
	}
	/**
	 * 답글쓰기
	 * @param board
	 * @throws AddException
	 */
	public void replyBoard(DriverBoard board) throws AddException{
		if(board.getBoardParentNo() == 0L) {
			throw new AddException("답글쓰기의 부모글번호가 없습니다");
		}
		repository.save(board);
	}

	// 글 수정
	public void modifyBoard(DriverBoard board) throws ModifyException {
		//repository.update(board);
		Optional<DriverBoard> optB = repository.findById(board.getCarBoardNo());
		if(!optB.isPresent()) {  // 존재하지 않을때
			throw new ModifyException("글이 없습니다");
		} else {
			DriverBoard b = optB.get();
//			b.setCarBoardContents(board.getCarBoardContents());
			repository.save(b);
		}
//		optB.ifPresent((b)->{
//
//		});
		
	}

	//글 삭제
	public void removeBoard(Long boardNo) {
		repository.deleteById(boardNo);  // 해당하는 글 삭제
		repository.findById(boardNo).orElseThrow(null);
		repository.deleteById(boardNo);
		
	}


}
