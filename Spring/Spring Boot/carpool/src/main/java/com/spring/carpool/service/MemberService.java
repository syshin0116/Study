package com.spring.carpool.service;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.entity.MemberEntity;
import com.spring.carpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;

    public String save(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
        String savedId = memberRepository.save(memberEntity).getMemberId();
        return savedId;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        login.html에서 입력받은 아이디, 비번을 받아오고
        DB로부터 해당 아이디의 정보를 가져와서
        입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
        일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        System.out.println("Service/login=================="+memberDTO);
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

    public MemberDTO findById(Long memberIdx) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberIdx);
        if (optionalMemberEntity.isPresent()) {
//            return MemberDTO.toMemberDTO((optionalMemberEntity.get()));
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }

    public MemberDTO findByMemberId(String memberId){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
        if (optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
        }else{
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity member: memberEntityList){
//            MemberDTO memberDTO = MemberDTO.toMemberDTO((member);
//            memberDTOList.add(memberDTO);
            memberDTOList.add(MemberDTO.toMemberDTO(member));
        }
        return memberDTOList;
    }

    public void delete(String memberId) {
        MemberDTO memberDTO = findByMemberId(memberId);
        long memberIdx = memberDTO.getMemberIdx();
        memberRepository.deleteById(memberIdx);
    }

    public void update(MemberDTO memberDTO){
        memberRepository.save(MemberEntity.toUpdateEntity(memberDTO));
    }

    public String idCheck(String memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
        if (optionalMemberEntity.isEmpty()){
            return "ok";
        }else{
            return "no";
        }
    }

    /*아이디 찾기 > 메일주소, 이름으로 찾는다. */
    public MemberDTO findByMemberEmailAndMemberName(MemberDTO memberDTO) {

        String memberEmail = memberDTO.getMemberEmail();
        String memberName = memberDTO.getMemberName();

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberName(memberEmail, memberName);

        if (optionalMemberEntity.isPresent()) {

            //	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());

            MemberEntity memberEntity = optionalMemberEntity.get();

            MemberDTO resultDTO = MemberDTO.toMemberDTO(memberEntity);

            return resultDTO;

        } else {
            return null;
        }
    }

    /*비밀번호 찾기 >아이디, 이름으로 찾는다. */
    public MemberDTO findByMemberIdAndMemberName(MemberDTO memberDTO) {

        String memberName = memberDTO.getMemberName();
        String memberId = memberDTO.getMemberId();


        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberIdAndMemberName(memberId, memberName);

        if (optionalMemberEntity.isPresent()) {

            //	 return MemberDTO.toMemberDTO(optionalMemberEntity.get());

            MemberEntity memberEntity = optionalMemberEntity.get();

            MemberDTO resultDTO = MemberDTO.toMemberDTO(memberEntity);

            return resultDTO;

        } else {
            return null;
        }
    }
}
