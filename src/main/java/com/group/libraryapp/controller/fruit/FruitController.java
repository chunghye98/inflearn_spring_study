package com.group.libraryapp.controller.fruit;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;
import com.group.libraryapp.dto.fruit.response.FruitCountResponse;
import com.group.libraryapp.dto.fruit.response.FruitResponse;
import com.group.libraryapp.service.fruit.FruitServiceV2;

@RestController
public class FruitController {

	private final FruitServiceV2 fruitService;

	public FruitController(FruitServiceV2 fruitService) {
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

	@GetMapping("/api/v1/fruit/count")
	public FruitCountResponse getFruitCount(@RequestParam String name) {
		return fruitService.getFruitCount(name);
	}

	@GetMapping("/api/v1/fruit/list")
	public List<FruitResponse> getFruitsInRange(@RequestParam String option, @RequestParam long price) {
		return fruitService.getFruitsInRange(option, price);
	}
}
