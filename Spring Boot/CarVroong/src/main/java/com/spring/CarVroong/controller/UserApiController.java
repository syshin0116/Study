package com.spring.CarVroong.controller;
import com.spring.CarVroong.model.Board;
import com.spring.CarVroong.model.QUser;
import com.spring.CarVroong.model.User;
import com.spring.CarVroong.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.spring.CarVroong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
        Iterable<User> users = null;
        if ("query".equals(method)) {
            users = repository.findByUsernameQuery(text);
        } else if ("nativeQuery".equals(method)) {
            users = repository.findByUsernameNativeQuery(text);
        } else if ("querydsl".equals(method)) {
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);
            users = repository.findAll(predicate);
        } else if ("querydslCustom".equals(method)) {
            users = repository.findByUsernameCustom(text);
        } else if ("jdbc".equals(method)) {
            users = repository.findByUsernameJdbc(text);
        } else {
            users = repository.findAll();
        }
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for (Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/users/idCheck")
    public @ResponseBody String idCheck(@RequestParam String username) {
        String checkResult = userService.idCheck(username);
        return checkResult;
    }


}