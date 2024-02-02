package kr.co.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.board.dao.BoardDao;
import kr.co.board.service.BoardService;
import kr.co.board.vo.BoardVo;

/*implements는 인터페이스를 상속받아서 구현하는 클래스*/

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	public BoardDao  boardDao;  // 파일의 이름이 Mapper나 Dao나 같은의미 구글검색ㄱ

	/*게시판 리스트*/
	public List<BoardVo> boardList(){
		return boardDao.boardList();
	}

	/*게시판 상세조회*/
	public BoardVo boardDetail(BoardVo vo) {
		return boardDao.boardDetail(vo);
	}

	/*게시판 저장*/
	public int boardInsert(BoardVo vo) {
		return boardDao.boardInsert(vo);
	}

	/*게시판 수정*/
	public int boardUpdate(BoardVo vo) {
		return boardDao.boardUpdate(vo);
	}
	
	/*게시판 삭제*/
	public int boardDelete(BoardVo vo) {
		return boardDao.boardDelete(vo);
	}
	
}
