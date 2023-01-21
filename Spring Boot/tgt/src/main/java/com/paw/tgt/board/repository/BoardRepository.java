package com.paw.tgt.board.repository;

import com.paw.tgt.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAll(PageRequest.of(page, 3,Sort.by(Enum.EnumDesc, "id")));
    List<Board> findByTitleOrContent(String bcTitle, String bcContents);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}