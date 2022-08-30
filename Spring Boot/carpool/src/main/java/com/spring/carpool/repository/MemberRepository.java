package com.spring.carpool.repository;

import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.dto.MemberDTO;
import com.spring.carpool.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // select * from member where member_id = ?
    // 리턴타입: MemberEntity
    // 매개변수: member_id(String)
    Optional<MemberEntity> findByMemberId(String memberId);
}
