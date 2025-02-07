package com.my.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.my.dto.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
	
	//@Query(value="",nativeQuery=true )
	List<Board> findAll(org.springframework.data.domain.Pageable paging);
	
	@Query(value="SELECT * "
			+ "FROM ( "
			+ "SELECT rownum r, a.* "
			+ "FROM ("
			+ "SELECT level, board_no, board_parent_no, "
			+ "board_title, board_content, board_id, board_dt, "
			+ "board_viewcount FROM board_jpa "
			+ "START WITH board_parent_no = 0 "
			+ "CONNECT BY PRIOR board_no = board_parent_no "
			+ "ORDER SIBLINGS BY board_no DESC ) a ) "
			+ "WHERE r BETWEEN ?1 AND ?2"
			, nativeQuery=true)
	List<Board> findByPage(int startRow, int endRow);
	
	
	@Query(value="SELECT * "
			+ "FROM ( SELECT rownum r, a.* "
			+ "FROM (SELECT level, board_no, board_parent_no, "
			+ "board_title, board_content, board_id, "
			+ "board_viewcount,board_dt "
			+ "FROM board_jpa "
			+ "WHERE board_title "
			+ "LIKE '%${word}%' OR board_id LIKE '%${word}%' "
			+ "START WITH board_parent_no = 0 "
			+ "CONNECT BY PRIOR board_no = board_parent_no "
			+ "ORDER SIBLINGS BY board_no DESC "
			+ ") a ) "
			+ "WHERE r BETWEEN ?1 AND ?2"
			,nativeQuery=true )
	List<Board> findByWord(String word);
	
	//글 삭제 부분
	@Modifying
	@Query(value="DELETE FROM board WHERE board_no = ?1"
			,nativeQuery=true )
	List<Board> delete(Long boardNo);
	
	//답글 삭제 부분
	@Modifying
	@Query(value="DELETE FROM board_jpa\r\n"
			+ "		WHERE board_no IN ( SELECT board_no\r\n"
			+ "		FROM board_jpa\r\n"
			+ "		START WITH board_parent_no = ?1\r\n"
			+ "		CONNECT BY PRIOR board_no = board_parent_no)"
			, nativeQuery=true)
	void deleteReply(Long boardNo);

	@Query(value="SELECT COUNT(*) FROM board_jpa",nativeQuery=true )
	Long selectCount();
}
