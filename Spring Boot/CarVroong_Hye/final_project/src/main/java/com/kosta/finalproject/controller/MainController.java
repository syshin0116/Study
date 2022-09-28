package com.kosta.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	// URL을 /로 접근하거나 /home/main으로 접근할 수 있다.
	@GetMapping ({"/","/home/main"})
	public String home() {
		return "home/main"; 

}

}
