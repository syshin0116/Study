package com.paw.tgt.user.service;

import com.paw.tgt.user.entity.Role;
import com.paw.tgt.user.entity.User;
import com.paw.tgt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
//        user.setEnabled(true);
        return userRepository.save(user);
    }

    //id 중복 검사
    public String idCheck(String username) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("result", userRepository.existsByUsername(username));
//        return map;

        if (userRepository.existsByUsername(username)){
            return "ok";
        }else{
            return "no";
        }
    }

    //닉네임 중복 검사
//    public HashMap<String, Object> nicknameOverlap(String nickname) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("result", userRepository.existsByNickname(nickname));
//        return map;
//    }
}
