package com.spring.carpool.service;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.entity.MemberEntity;
import com.spring.carpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
        Long savedId = memberRepository.save(memberEntity).getMemberIdx();
        return savedId;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        login.html에서 입력받은 아이디, 비번을 받아오고
        DB로부터 해당 아이디의 정보를 가져와서
        입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
        일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if (loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toMemberDTO(loginEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public MemberDTO findById(Long member_idx) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(member_idx);
        if (optionalMemberEntity.isPresent()) {
//            return MemberDTO.toMemberDTO((optionalMemberEntity.get()));
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }
}
