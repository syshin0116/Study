package com.paw.tgt.board.repository;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.board.entity.BoardCate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository1 extends JpaRepository<Board, Long> {
    List<Board> findAllByBoardCate(BoardCate boardCate, Pageable pageable);
    @Override
    List<Board> findAll();
    //    Page<Board> findAll(PageRequest.of(page, 3,Sort.by(Enum.EnumDesc, "id")));
//    List<Board> findByTitleOrContent(String bcTitle, String bcContents);
//    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}