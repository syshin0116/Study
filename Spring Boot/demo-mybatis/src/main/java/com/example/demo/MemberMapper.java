package com.example.demo;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
	
	@Insert("INSERT INTO member(id, pwd, name) VALUES (#{member.id}, #{member.pwd}, #{member.name})")
	@Options(useGeneratedKeys = true, keyProperty = "idx")
	int insert(@Param("member") Member member);
	
	@Select("SELECT * FROM member")
	@Results(id="MemberMap", value={
		@Result(property="id", column="id"),
		@Result(property="pwd", column="pwd"),
		@Result(property="name", column="name")
	})
	List<Member> getAll();
	
	@Select("SELECT * FROM member WHERE idx=#{idx}")
	@ResultMap("MemberMap")
	Member getById(@Param("idx") int idx);
}
