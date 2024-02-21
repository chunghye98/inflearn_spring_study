package com.group.libraryapp.controller.calculator;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import com.group.libraryapp.dto.calculator.request.SumRequest;
import com.group.libraryapp.dto.calculator.response.CalculatorResponse;
import com.group.libraryapp.dto.calculator.response.DateResponse;

@RestController
public class CalculatorController {

	@GetMapping("/add") // GET /add
	public int addTwoNumbers(CalculatorAddRequest request){
		return request.getNumber1() + request.getNumber2();
	}

	@PostMapping("/multiply") // POST /multiply
	public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
		return request.getNumber1() * request.getNumber2();
	}

	@GetMapping("/api/v1/calc")
	public CalculatorResponse calculator(@RequestParam int num1, @RequestParam int num2){
		return new CalculatorResponse(num1 + num2, num1 - num2, num1 * num2);
	}

	@GetMapping("/api/v1/date")
	public DateResponse getDate(@RequestParam String date) {
		LocalDate localDate = LocalDate.parse(date);
		return new DateResponse(localDate.getDayOfWeek().name());
	}

	@PostMapping("/api/v1/sum")
	public int getSum(@RequestBody SumRequest request) {
		int sum = 0;
		for (Integer value : request.getNumbers()) {
			sum += value;
		}
		return sum;
	}

}
