package com.group.libraryapp.controller.fruit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;
import com.group.libraryapp.service.fruit.FruitService;

@RestController
public class FruitController {

	private final FruitService fruitService;


	public FruitController(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@PostMapping("/api/v1/fruit")
	public void createFruit(@RequestBody FruitCreateRequest request) {
		fruitService.createFruit(request);
	}

	@PutMapping("/api/v1/fruit")
	public void updateFruit(@RequestBody FruitUpdateRequest request) {
		fruitService.updateFruit(request);
	}

	@GetMapping("/api/v1/fruit/stat")
	public FruitAmountReadResponse getFruitByStatus(@RequestParam String name) {
		return fruitService.getFruitByStatus(name);
	}
}
