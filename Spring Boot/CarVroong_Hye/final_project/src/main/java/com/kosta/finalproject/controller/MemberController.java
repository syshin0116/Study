package com.kosta.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")

public class MemberController {
	private final MemberService memberService;
	
	/*로그인 페이지 이동*/
	@GetMapping("/loginForm")
	public String loginForm() {
		return "memberPages/login";
	}

	/*회원가입 페이지 이동*/
	@GetMapping("/joinForm")
	public String joinForm() {
		return "memberPages/join";
	}	
	
	
	/*마이페이지 상세보기 이동*/
	@GetMapping("/myPageForm")
	public String myPageForm() {
		return "memberPages/myPage";
	}	

	/*마이페이지 수정페이지 이동*/
	@GetMapping("/myPageUpdateForm")
	public String myPageUpdateForm() {
		return "memberPages/myPageUpdate";
	}	
	
	@PostMapping("/save")
	public String save(@ModelAttribute MemberDTO memberDTO) {
		memberService.save(memberDTO);
		return "memberPages/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login (MemberDTO memberDTO, HttpSession session) {

		log.info("memberDTO >>>>>>>>>>>>>>>>> : "+memberDTO.toString());
		
		MemberDTO loginResult = memberService.login(memberDTO);
		
		if (loginResult != null) {
			//로그인정보를 세션에 담는다
			session.setAttribute("loginInfo", loginResult);
		//	memberDTO = (MemberDTO) session.getAttribute("loginInfo");
			
			return "success";	
		}else {
			return "fail";	
		}
		
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//모든 세션을 삭제
		session.invalidate();
		return "redirect:/home/main";
		
	}	
	
	@GetMapping("/")
	public String findAll(Model model) {
		List<MemberDTO> memberDTOList =memberService.findAll();
		model.addAttribute("memberList", memberDTOList);
		return "memberPages/list";
		
	}
	// /member/3
	//  /member?id=3
	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model) {
		MemberDTO memberDTO = memberService.findById(id);
		model.addAttribute("member",memberDTO);
		return "memberPages/detail";
		
		
	}
	//ajax상세 조회
	@PostMapping("/ajax/{id}")
	public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id) {
		MemberDTO memberDTO = memberService.findById(id);
		return memberDTO;
	}
	
	//get 요청 삭제 /member/delete/3
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id ) {
		memberService.delete(id);
		return "redirect:/member/";
	//	return"memberPages.list";//X
	}
	
	 /**
	  * /member/3: 조회(get) R, 저장(post) C, 수정(put) U, 삭제(delete)  D
	  */
	
	
	
		//delete요청 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity deleteAjax(@PathVariable Long id) {
		memberService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK); //ajax 호출한 부분에 리턴으로 200 응답을 줌.
		
	}
	}
