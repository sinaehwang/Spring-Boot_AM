package com.hsn.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UerHomeController {
	private int count = 0;
	
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
	
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public int showMain4() {
		
		return count++; //후위연산자로 count값먼저 반환후 +1이기 때문에 0부터 시작됨

	}

	@RequestMapping("/usr/home/main5")
	@ResponseBody
	public String showMain5() {
		count =0 ;
		
		return "count의 값을 0으로 초기화";

	}
	

}
