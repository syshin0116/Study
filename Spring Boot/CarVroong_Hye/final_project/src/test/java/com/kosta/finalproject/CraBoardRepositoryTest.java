package com.kosta.finalproject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.finalproject.dto.DriverBoard;
import com.kosta.finalproject.repository.DriverBoardRepository;
@SpringBootTest
class CraBoardRepositoryTest {
	@Autowired
	DriverBoardRepository repository;
	@Test
	void testFindById() {
		Long carBoardNo = 1L;
		//게시글번호의 게시글 조회한다
		//Board b1 = repository.selectByBoardNo(boardNo);
		Optional<DriverBoard> optB1 = repository.findById(carBoardNo);
		assertTrue(optB1.isPresent());
	}
	
	@Test
	void testFindByIdInValid() {
		Long carBoardNo = 1L;
		Optional<DriverBoard> optB1 = repository.findById(carBoardNo);
		assertTrue(optB1.isPresent());
	}

	@Test
	// @Transactional  // DB에 실제로 commit되지는 않는다
	void testWrite() {
		DriverBoard b = new DriverBoard();
//		b.setCarBoardNo(13L);
		b.setUserId("id1");
		b.setCarBoardTitle("title_1");
		b.setCarBoardStatus(0L);
		b.setStartPoint("파주");
		b.setEndPoint("성남");
//		b.setStartTime(null);  // 원래는 반드시 입력해야 되지만 테스트를 위해
		b.setCarInfo("그랜저");
		b.setMaxSeat(3L);
		b.setUserLike(2L);
		b.setCarBoardContents("content_t1");
		b.setBoardParentNo(null);
		b.setBoardViewcount(3);
		repository.save(b);
	}
	
	@Test
	void testReply() {
		DriverBoard b = new DriverBoard(); 
		b.setCarBoardNo(1L); // 1번글의 답글
		b.setCarBoardTitle("1_re_title");
		b.setCarBoardContents("1_recontent");
		b.setUserId("id3");
		repository.save(b);
	}
	
	@Test
	void testModify() {
		Optional<DriverBoard> optB = repository.findById(2L);
		optB.ifPresent((b)->{ // 다르게 설정된 값만 변경
			b.setCarBoardTitle("수정글");
			b.setCarBoardContents("글 내용 수정");
			repository.save(b);
		});
	}
	
	@Test
	void testDelete() {
		Long carBoardNo = 1L;
		repository.deleteById(carBoardNo);
		assertFalse(repository.findById(carBoardNo).isPresent());
	}
}
