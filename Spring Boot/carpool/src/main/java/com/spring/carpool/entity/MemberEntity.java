package com.spring.carpool.entity;

import com.spring.carpool.dto.MemberDTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter @Setter
@Table(name="member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_idx")
    private Long memberIdx;

    @Column(length=50, unique = true, name="member_id")
    private String memberId;

    @Column(length=20, name="member_password")
    private String memberPassword;

    @Column(length=20, name="member_name")
    private String memberName;

    @Column(length=50, unique = true, name="member_email")
    private String memberEmail;

    @Column(length=20, name="member_license")
    private String memberLicense;

    @Column(name="member_birthdate")
    private Date memberBirthDate;

    @Column(name="member_points")
    private int memberPoints;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberIdx(memberDTO.getMemberIdx());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberLicense(memberDTO.getMemberLicense());
        memberEntity.setMemberBirthDate(memberDTO.getMemberBirthDate());
//        memberEntity.setMember_points(memberEntity.getMember_points());

        return memberEntity;
    }
}
