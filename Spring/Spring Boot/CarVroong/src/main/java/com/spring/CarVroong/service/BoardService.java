package com.spring.CarVroong.service;

import com.spring.CarVroong.model.Board;
import com.spring.CarVroong.model.User;
import com.spring.CarVroong.repository.BoardRepository;
import com.spring.CarVroong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
