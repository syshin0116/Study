package com.paw.tgt.board.service;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.board.repository.BoardRepository;
import com.paw.tgt.board.repository.BoardRepository1;

import com.paw.tgt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

}
