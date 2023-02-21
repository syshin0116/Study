package com.paw.tgt.user.controller;

import com.paw.tgt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    /*아이디 중복체크*/
    @PostMapping("/idCheck")
    public @ResponseBody String idCheck(@RequestParam String username){
        String checkResult = userService.idCheck(username);
        System.out.println("UserController>>>IdCheck");

        return checkResult;
    }

}
