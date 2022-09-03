package com.spring.carpool.dto;

import com.spring.carpool.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long memberIdx;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberLicense;
    private Date memberBirthDate;
    private String memberMobile;
    private int memberPoints; // 포인트제도: db에 default=0으로 설정되있음
//    private List<Car> carList; // 사용자의 동록된 차 list 가져오기 위함이지만 @Entity

//    private List<Car> carList; // 사용자의 동록된 차 list 가져오기 위함이지만 @Entity 쓰면 에러남

    public MemberDTO(String memberId, String memberPassword, String memberName, String memberEmail, String memberLicense, Date memberBirthDate, String memberMobile, int memberPoints) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberLicense = memberLicense;
        this.memberBirthDate = memberBirthDate;
        this.memberMobile = memberMobile;
        this.memberPoints = memberPoints;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberIdx((memberEntity.getMemberIdx()));
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberLicense(memberEntity.getMemberLicense());
        memberDTO.setMemberBirthDate(memberEntity.getMemberBirthDate());
        memberDTO.setMemberMobile((memberEntity.getMemberMobile()));
        memberDTO.setMemberPoints(memberEntity.getMemberPoints());
        return memberDTO;
    }
}