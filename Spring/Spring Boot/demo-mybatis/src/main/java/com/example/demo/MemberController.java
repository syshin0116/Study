package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@PostMapping("")
	public Member post(@RequestBody Member member) {
		memberMapper.insert(member);
		return member;
	}

	@GetMapping("")
	public List<Member> getAll(){
		return memberMapper.getAll();
	}
	
	@GetMapping("/{idx}")
	public Member getById(@PathVariable("idx") int idx){
		return memberMapper.getById(idx);
	}
}