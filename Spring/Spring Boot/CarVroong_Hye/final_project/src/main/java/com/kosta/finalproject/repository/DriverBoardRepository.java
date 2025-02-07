package com.kosta.finalproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.finalproject.dto.DriverBoard;

public interface DriverBoardRepository extends CrudRepository<DriverBoard, Long> {
	List<DriverBoard> findAll(org.springframework.data.domain.Pageable paging);
	// SQL구문 바꿔야됨
	@Query(value="SELECT *\r\n"
			+ "FROM (\r\n"
			+ "  SELECT rownum r, a.*\r\n"
			+ "  FROM (SELECT level, \r\n"
			+ "          CARBOARDNUM, USERID, CARBOARDTITLE, CARBOARDSTATUS, STARTPOINT, ENDPOINT, STARTTIME, CARINFO, MAXSEAT, USERLIKE, CARBOARDCONTENTS, PARENTNO, VIEWCOUNT\r\n"
			+ "          FROM CAR_BOARD \r\n"
			+ "          START WITH PARENTNO = 0\r\n"
			+ "          CONNECT BY PRIOR CARBOARDNUM = PARENTNO \r\n"
			+ "          ORDER SIBLINGS BY CARBOARDNUM DESC\r\n"
			+ "  ) a\r\n"
			+ ")\r\n"
			+ "WHERE r BETWEEN ?1 AND ?2",
			nativeQuery = true)  // 일반 sql구문을 쓸 수 있다
	List<DriverBoard> findByPage(int startRow, int endRow); // 검색
	
	@Modifying
	@Query(value = "DELETE FROM CAR_BOARD\r\n"
			+ "WHERE board_no IN ( SELECT  CARBOARDNUM \r\n"
			+ "                  FROM CAR_BOARD\r\n"
			+ "                  START WITH PARENTNO = ?1\r\n"
			+ "                  CONNECT BY PRIOR CARBOARDNUM = PARENTNO)", 
			nativeQuery = true)
	void deleteReply(Long boardNo);  // 삭제
}
