package com.spring.carpool.controller;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.entity.MemberEntity;
import com.spring.carpool.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            session.setAttribute("loginIdx", loginResult.getMemberIdx());
            session.setAttribute("loginId", loginResult.getMemberId());
            session.setAttribute( "loginName", loginResult.getMemberName());
//            session.setAttribute(name:"loginUserData", MemberService.selectLogin(MemberEntity));
            return "memberPages/main";
        }else{
            return "memberPages/login";
        }
    }
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/list";
    }

    // member/회원idx
    @GetMapping("/{memberId}")
    public String findByIdx(@PathVariable String memberId, Model model){
        MemberDTO memberDTO = memberService.findByMemberId(memberId);
        model.addAttribute("member", memberDTO);
        return "memberPages/detail";
    }
    //ajax 상세조회
    @PostMapping("/ajax/{memberId}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable String memberId){
        MemberDTO memberDTO = memberService.findByMemberId(memberId);
        return memberDTO;
    }
    // get 요청 삭제
    @GetMapping("/delete/{memberId}")
    public String delete(@PathVariable String memberId){
        memberService.delete(memberId);
        return "redirect:/member/";
        //return "memberPages/memberList";
    }
    /**
     * /member/3 : 조회(get), 저장(post), 수정(put), 삭제(delete)
     */
    // delete 요청 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteAjax(@PathVariable String memberId){
        memberService.delete(memberId);
        return new ResponseEntity<>(HttpStatus.OK); // ajax 호출한 부분에 리턴으로 200 응답을 줌
    }
    //수정
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        Long memberIdx = (Long)session.getAttribute("loginIdx");
        MemberDTO memberDTO = memberService.findById(memberIdx);
        model.addAttribute("updateMember", memberDTO);
        return "memberPages/update";
    }

    //수정처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getMemberId();
    }
}
