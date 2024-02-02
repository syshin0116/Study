package com.kosta.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import com.kosta.finalproject.dto.MemberDTO;
import com.kosta.finalproject.entity.MemberEntity;
import com.kosta.finalproject.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	
	public Long save(MemberDTO memberDTO) {
		
		//MemberEntity memberEntity = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)); 						
		MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
		Long savedId = memberRepository.save(memberEntity).getMemberNo();
		return savedId;
		
	}

	public MemberDTO login(MemberDTO memberDTO) {
		/**
		 * login.html에서 아이디, 비번을 받아오고
		 * DB 로부터 해당 이메일의 정보를 가져와서
		 * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
		 * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
		 */
		// return false;
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());	
		
		
		log.info("optionalMemberEntity >>>>>>>>>>>>>>>>> "+optionalMemberEntity.toString());
	
		if (optionalMemberEntity.isPresent()) {

			MemberEntity loginEntity = optionalMemberEntity.get();
			
			if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
				
				return MemberDTO.toMemberDTO(loginEntity);
			
			} else {
				
				return null;
			}
			
		}else {
			
			return null;
		
		}
	}

	public MemberDTO findById(Long id) {
	 Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
	 if (optionalMemberEntity.isPresent()) {
	//	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		 MemberEntity memberEntity = optionalMemberEntity.get();
		 MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
		 return memberDTO;
	 } else {
		 return null;
	 }
		 
 }

public List<MemberDTO> findAll() {
	List<MemberEntity> memberEntityList = memberRepository.findAll();
	List<MemberDTO> memberDTOList = new ArrayList<>();
	for (MemberEntity member: memberEntityList) {
		//MemberDTO memberDTO = MemberDTO.toMemberDTO(member);
		//memberDTOList.add(memberDTO);
		memberDTOList.add(MemberDTO.toMemberDTO(member));
	}
	return memberDTOList;
}

public void delete(Long id) {
	memberRepository.deleteById(id);
	
	
}
	}
