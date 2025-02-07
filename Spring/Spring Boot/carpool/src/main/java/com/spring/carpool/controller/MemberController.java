package com.spring.carpool.controller;

import com.spring.carpool.dto.MemberDTO;
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

    /*회원가입 페이지 요청*/
    @GetMapping("/save-form")
    public String saveForm(){
        return "member/signup";
    }

    /*저장(회원가입)*/
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("Controller/save================================"+memberDTO);
        memberService.save(memberDTO);
        return "login-form";
    }

   

    /*회원 관리(목록) 페이지*/
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "member/list";
    }

    /*상세조회*/
    //member/회원id
    @GetMapping("/{memberId}")
    public String findByIdx(@PathVariable String memberId, Model model){
        MemberDTO memberDTO = memberService.findByMemberId(memberId);
        model.addAttribute("member", memberDTO);
        return "member/detail";
    }

    /*ajax 상세조회*/
    @PostMapping("/ajax/{memberId}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable String memberId){
        MemberDTO memberDTO = memberService.findByMemberId(memberId);
        return memberDTO;
    }

    /*(get)삭제*/
    @GetMapping("/delete/{memberId}")
    public String delete(@PathVariable String memberId){
        memberService.delete(memberId);
        return "redirect:/member/";
        //return "member/memberList";
    }
    /**
     * /member/3 : 조회(get), 저장(post), 수정(put), 삭제(delete)
     */

    /*(delete)삭제 요청*/
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteAjax(@PathVariable String memberId){
        memberService.delete(memberId);
        return new ResponseEntity<>(HttpStatus.OK); // ajax 호출한 부분에 리턴으로 200 응답을 줌
    }

    /*수정 페이지 요청*/
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        Long memberIdx = (Long)session.getAttribute("loginIdx");
        MemberDTO memberDTO = memberService.findById(memberIdx);
        model.addAttribute("updateMember", memberDTO);
        return "member/update";
    }

    /*수정처리 요청*/
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getMemberId();
    }

    /*(put)수정 처리*/
    @PutMapping("/{memberId}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO){
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*아이디 중복체크*/
    @PostMapping("/idCheck")
    public @ResponseBody String idCheck(@RequestParam String memberId){
        String checkResult = memberService.idCheck(memberId);
        return checkResult;
    }

    /*마이페이지 상세보기*/
    @GetMapping("/myPage")
    public String myPageForm(Model model, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginInfo");
        model.addAttribute("member", memberDTO);
        System.out.println("==================="+memberDTO);
        return "member/myPage";
    }

    /*마이페이지 수정페이지*/
    @GetMapping("/myPageUpdateForm")
    public String myPageUpdateForm() {
        return "member/myPageUpdate";
    }

}
