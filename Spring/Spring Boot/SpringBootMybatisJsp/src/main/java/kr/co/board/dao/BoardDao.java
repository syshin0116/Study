package kr.co.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.board.vo.BoardVo;

@Repository
@Mapper
public interface BoardDao {
    
    //게시글 목록  
    List<BoardVo> boardList();

    //게시글 상세조회
    BoardVo boardDetail(BoardVo vo);
    
    //게시글 작성  
    int boardInsert(BoardVo vo);

    //게시글 수정
    int boardUpdate(BoardVo vo);

    //게시글 삭제 
    int boardDelete(BoardVo vo);
    
}
