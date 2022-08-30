package com.my.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.my.dto.Board;

@SpringBootTest
class BoardRepositoryTest {
	
	@Autowired
	BoardRepository repository;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	void testFindByIdValud() { //board가 있을떄, 없을때를 생각해야함
		Long boardNo = 1L;
		Optional<Board> optB1 = repository.findById(boardNo);
		assertTrue(optB1.isPresent());
		
	}
	@Test
	void testFindByIdInValid() { //board가 있을떄, 없을때를 생각해야함
		Long boardNo = 1L;
		Optional<Board> optB1 = repository.findById(boardNo);
		assertFalse(optB1.isPresent());
		
	}
	@Test
	//@Transactional
	void testWrite() {
		Board b = new Board();
		b.setBoardTitle("title_t2");
		b.setBoardContent("content_t2");
		b.setBoardId("id2");
		b.setBoardParentNo(0L);
		repository.save(b);
		// 테스트할때마다 save를하면 데이터가 계속 바뀌므로 롤백 처리를 @Transaction 으로함
		
	}
	
	@Test
	void testReply() {
		Board b = new Board();
		b.setBoardParentNo(1L);
		b.setBoardTitle("1_re_title");
		b.setBoardContent("1_re_content");
		b.setBoardId("id2");
		repository.save(b);
	}

	@Test
	void testModify() {
		Board board = new Board();
//		board.setBoardNo(1L);
//		board.setBoardContent("글1 내용수정");
//		repository.save(board);
		
		Optional<Board>optB = repository.findById(4L);
		optB.ifPresent((b)->{
			b.setBoardContent("글4내용수정");
			repository.save(b);
		});
	}
	@Test
	void testUpdateViewCount() {
		Long boardNo = 1L;
		Optional<Board>optB = repository.findById(boardNo);
		optB.ifPresent((b)->{
			int oldViewCount = b.getBoardViewcount();
			int newViewCount = oldViewCount+1;
			b.setBoardViewcount(newViewCount);
			repository.save(b);
			
			int expectedNewViewCount=newViewCount;
			assertEquals(expectedNewViewCount,repository);
		});
	}
	@Transactional
	@Test
	void testDlete() {
		Long boardNo = 4L;
		repository.deleteReply(boardNo);
		repository.deleteById(boardNo);
		assertFalse(repository.findById(boardNo).isPresent() );
	}
	
	@Test
	void testFindAllPage() {
		int currentPage = 2;
		Pageable pageable = PageRequest.of(currentPage, 4);
		// 보고싶은 페이지 : 2, 한페이지당 보여줄 건수 : 4
		List<Board> list = repository.findAll(pageable);
		list.forEach((b)->{
			logger.error(b.toString());
		});
	}
	
	@Test
	void testFindByPage() {
		int currentPage = 1;
		int cntPerPage = 3;
		int endRow = currentPage * cntPerPage;
		int startRow = endRow - cntPerPage +1;
		List<Board> list = repository.findByPage(startRow, endRow);
		list.forEach((b)->{
				logger.error(b.toString());
		});
	}
}
