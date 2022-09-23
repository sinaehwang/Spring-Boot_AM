package com.hsn.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UerHomeController {
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return("안녕");
	}
	
	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return("반갑");
	}

	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public String showMain3() {
		return("잘가");
	}

	

}
