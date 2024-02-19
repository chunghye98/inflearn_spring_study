package com.group.libraryapp.controller.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.controller.dto.calculator.request.CalculatorAddRequest;

@RestController
public class CalculatorController {

	@GetMapping("/add") // GET /add
	public int addTwoNumbers(CalculatorAddRequest request){
		return request.getNumber1() + request.getNumber2();
	}

}
