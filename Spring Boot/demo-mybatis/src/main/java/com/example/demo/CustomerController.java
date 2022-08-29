package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@PostMapping("")
	public int post(@RequestBody Customer customer) {
		return customerMapper.insert(customer);
	}

	@GetMapping("")
	public List<Customer> getAll(){
		return customerMapper.getAll();
	}
}