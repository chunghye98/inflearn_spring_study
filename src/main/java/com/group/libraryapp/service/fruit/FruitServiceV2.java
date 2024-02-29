package com.group.libraryapp.service.fruit;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.domain.fruit.FruitRepository;
import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;
import com.group.libraryapp.dto.fruit.response.FruitCountResponse;
import com.group.libraryapp.dto.fruit.response.FruitResponse;

@Service
public class FruitServiceV2 {

	private final FruitRepository fruitRepository;

	public FruitServiceV2(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	public void createFruit(FruitCreateRequest request) {
		fruitRepository.save(new Fruit(request.getName(), request.getPrice(), request.getWarehousingDate()));
	}

	public void updateFruit(FruitUpdateRequest request) {
		Fruit fruit = fruitRepository.findById(request.getId())
			.orElseThrow(IllegalArgumentException::new);

		fruit.updateStatus(true);
		fruitRepository.save(fruit);
	}

	public FruitAmountReadResponse getFruitByStatus(String name) {
		List<Fruit> salesFruits = fruitRepository.findAllByNameAndStatus(name, true);
		List<Fruit> unSalesFruits = fruitRepository.findAllByNameAndStatus(name, false);

		long salesAmount = salesFruits.stream()
			.mapToLong(Fruit::getPrice)
			.sum();
		long unSalesAmount = unSalesFruits.stream()
			.mapToLong(Fruit::getPrice)
			.sum();

		return new FruitAmountReadResponse(salesAmount, unSalesAmount);
	}

	public FruitCountResponse getFruitCount(String name) {
		long count = fruitRepository.countByName(name);
		return new FruitCountResponse(count);
	}

	public List<FruitResponse> getFruitsInRange(String option, long price) {
		List<Fruit> fruits;
		if (option.equals("GTE")) {
			fruits = fruitRepository.findAllByPriceGreaterThanEqualAndStatus(price, false);
		} else if (option.equals("LTE")) {
			fruits = fruitRepository.findAllByPriceLessThanEqualAndStatus(price, false);
		} else {
			throw new IllegalArgumentException();
		}

		return fruits.stream()
			.map(FruitResponse::new)
			.collect(Collectors.toList());
	}
}
