package kr.co.board.service;

import java.util.List;

import kr.co.board.vo.BoardVo;

public interface BoardService {

	/*게시판 리스트*/
	public List<BoardVo> boardList();

	/*게시판 상세조회*/
	public BoardVo boardDetail(BoardVo vo);

	/*게시판 저장*/
	public int boardInsert(BoardVo vo);

	/*게시판 수정*/
	public int boardUpdate(BoardVo vo);

	/*게시판 삭제*/
	public int boardDelete(BoardVo vo);
	
}
