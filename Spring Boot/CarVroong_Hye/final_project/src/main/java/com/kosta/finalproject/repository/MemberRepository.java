package com.kosta.finalproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.finalproject.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	//select * from member_table where memberEmail =? 
	//리턴타입: MemberEntity
	//매개변수: MemberId(String)
	Optional<MemberEntity> findByMemberId(String memberId);



}