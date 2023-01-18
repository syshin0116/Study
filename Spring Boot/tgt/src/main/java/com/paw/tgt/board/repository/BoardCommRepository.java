package com.paw.tgt.board.repository;

import com.paw.tgt.board.entity.BoardComm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommRepository extends JpaRepository<BoardComm, Long> {
    List<BoardComm> findAll();
    List<BoardComm> findByTitleOrContent(String bcTitle, String bcContents);
}