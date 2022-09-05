package com.spring.CarVroong.controller;

import com.spring.CarVroong.model.User;
import com.spring.CarVroong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("idCheck")
    public @ResponseBody HashMap<String, Object> idCheck(@RequestParam String username){
        HashMap<String, Object> checkResult = userService.idCheck(username);
        System.out.println("UserController================"+checkResult);
        return checkResult;
    }
}
