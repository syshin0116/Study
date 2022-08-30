package com.spring.carpool;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import  static  org.assertj.core.api.Assertions.*;
// 자바에서 제공하는 assert framework
import java.sql.Date;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(){
        MemberDTO member = new MemberDTO("테스트용아이디", "테스트용비밀번호", "테스트용이름", "이메일", "면허번호", Date.valueOf("2022-08-22"), 0);
        return member;
    }
    @Test
    @Transactional
    //Test에서는 @Transactional이 있으면 @Rollback 안써줘도 무관
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        Long savedId = memberService.save(newMember());
        MemberDTO memberDTO = memberService.findById(savedId);
        assertThat(newMember().getMemberId()).isEqualTo(memberDTO.getMemberId());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest(){
        final String memberId = "hyepark";
        final String memberPassword = "hyepark";
        String memberName = "로그인 이름";
        String memberEmail = "로그인 이메일";
        String memberLicense = "로그인 면허번호";
        Date date = new Date(2022, 8, 26);
        Date memberBirthDate = date;
        int memberPoints = 200;
        MemberDTO memberDTO = new MemberDTO(memberId, memberPassword, memberName, memberEmail, memberLicense, memberBirthDate, memberPoints);
        Long savedId = memberService.save(memberDTO);

        //로그인 샛체 생성 수 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberId(memberId);
        loginMemberDTO.setMemberPassword(memberPassword);
//        loginMemberDTO.setMemberName(memberName);
//        loginMemberDTO.setMemberEmail(memberEmail);
//        loginMemberDTO.setMemberLicense(memberLicense);
//        loginMemberDTO.setMemberBirthDate(memberBirthDate);
//        loginMemberDTO.setMemberPoints(memberPoints);
        MemberDTO loginResult = memberService.login((loginMemberDTO));
        // 로그인 결과가 not null 이면 테스트 통과
        assertThat(loginResult).isNotNull();


    }
}
