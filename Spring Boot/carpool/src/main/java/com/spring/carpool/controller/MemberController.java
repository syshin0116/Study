package com.spring.carpool.controller;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.entity.MemberEntity;
import com.spring.carpool.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "memberPages/save";
    }

    @GetMapping("/login-form")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        memberService.save(memberDTO);
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null){
            session.setAttribute("loginId", loginResult.getMemberId());
            session.setAttribute( "loginName", loginResult.getMemberName());
//            session.setAttribute(name:"loginUserData", MemberService.selectLogin(MemberEntity));
            return "memberPages/main";
        }else{
            return "memberPages/login";
        }
    }

}
