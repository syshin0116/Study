package com.kosta.finalproject.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="test_tbl")
@Getter @Setter
public class datatest {
	@Id
	@Column(name="a1", length=5)
	private String id;
	
	@Column(name="a10", length=30)
	private String testname;
	
}

