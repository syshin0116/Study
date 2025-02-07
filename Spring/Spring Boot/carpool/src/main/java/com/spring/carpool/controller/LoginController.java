package com.spring.carpool.controller;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")

public class LoginController {
    private final MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /*로그인 페이지 요청*/
    @GetMapping("/loginForm")
    public String loginForm() {
        return "login/login-form";
    }

    /*로그인*/
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        System.out.println("Controller/login================================"+loginResult);

        //로그인 성공시 메인화면, 실패시 다시 로그인 페이지로
        if (loginResult != null){
            session.setAttribute("loginInfo", loginResult); // 세션에 로그인한 memberDTO 저장
            return "main";
        }else{
            return "login/login-form";
        }
    }

    /*로그아웃 기능*/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); //모든 세션을 삭제
        return "main";
    }

    /*아이디 찾기 페이지 이동*/
    @GetMapping("/findIdForm")
    public String findIdForm() {
        return "login/findId-form";
    }

    /*아이디 찾기 조회*/
    @PostMapping("/findId")
    @ResponseBody
    public String findId(MemberDTO memberDTO) {


        String result = "";

        MemberDTO resultDTO = memberService.findByMemberEmailAndMemberName(memberDTO);

        if(resultDTO != null) {

            result = resultDTO.getMemberId().toString();

        }

        return result;

    }

    /*비밀번호 찾기 페이지 이동*/
    @GetMapping("/findPassword")
    public String findPwForm() {
        return "login/findPassword-form";
    }

    /*비밀번호 찾기 조회*/
    @PostMapping("/findPassword")
    @ResponseBody
    public String findPw(MemberDTO memberDTO, HttpSession session) {

//        log.info("비밀번호찾기 조회 findId ");

        String result = "";

        MemberDTO resultDTO = memberService.findByMemberIdAndMemberName(memberDTO);

        if(resultDTO != null) {

            //랜덤 비밀번호 생성

            String tempPw = getRandomPassword(8);

//            log.info("임시비밀번호 값 tempPw : "+tempPw);

            // 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
            String encodePw = passwordEncoder.encode(tempPw);

            resultDTO.setMemberPassword(encodePw);

            //내가생각한건.. hye0826 회원정보의 비번을 생성한 임시비번으로 업데이트 칠라고했음..
            //하지만 새로운 1줄이 인서트됨, 이슈
            memberService.save(resultDTO);

            result = tempPw;

        }

        return result;

    }

    /*임시비밀번호 발급 로직*/
    public String getRandomPassword(int size) {

        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}