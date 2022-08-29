package com.example.demo;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerMapper {
	
	@Insert("INSERT INTO customer(id, pwd, name) VALUES (#{customer.id}, #{customer.pwd}, #{customer.name})")
	int insert(@Param("customer") Customer customer);
	
	@Select("SELECT * FROM customer")
	@Results({
		@Result(property="id", column="id"),
		@Result(property="pwd", column="pwd"),
		@Result(property="name", column="name")
	})
	List<Customer> getAll();
}
