package com.group.libraryapp.service.fruit;

import org.springframework.stereotype.Service;

import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;
import com.group.libraryapp.repository.fruit.FruitMySqlRepository;

@Service
public class FruitServiceV1 {

	private final FruitMySqlRepository fruitRepository;

	public FruitServiceV1(FruitMySqlRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	public void createFruit(FruitCreateRequest request) {
		fruitRepository.createFruit(request.getName(), request.getPrice(), request.getWarehousingDate());
	}

	public void updateFruit(FruitUpdateRequest request) {
		boolean isExistFruit = fruitRepository.isExistFruit(request.getId());
		if (isExistFruit) {
			throw new IllegalArgumentException("존재하지 않는 과일입니다.");
		}

		fruitRepository.updateFruit(request.getId());
	}

	public FruitAmountReadResponse getFruitByStatus(String name) {
		return fruitRepository.getFruitByStatus(name);
	}
}
