package com.hsn.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UerHomeController {
	private int count = -1;
	
	@RequestMapping("/usr/home/getCount")
	@ResponseBody
	public int getCount() {
		
		return count; //후위연산자로 count값먼저 반환후 +1이기 때문에 0부터 시작됨

	}

	@RequestMapping("/usr/home/doSetCount") //액션
	@ResponseBody
	public String doSetCount(int count) {//카운트를 파라미터로 사용
		this.count = count;
		
		return "count의 값을 "+this.count+"으로 초기화"; //파라미터로 입력받은값이 나타남

	}
	

}
