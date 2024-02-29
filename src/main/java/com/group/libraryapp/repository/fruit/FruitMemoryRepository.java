package com.group.libraryapp.repository.fruit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;

@Repository
public class FruitMemoryRepository implements FruitJdbcRepository {
	private final List<Fruit> fruits = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong();

	@Override
	public void createFruit(String name, long price, LocalDate warehousingDate) {
		fruits.add(new Fruit(counter.incrementAndGet(), name, price, warehousingDate, false));
	}

	@Override
	public void updateFruit(long id) {
		fruits.stream()
			.filter(fruit -> fruit.getId() == id)
			.findFirst()
			.ifPresent(fruit -> fruit.setStatus(true));
	}

	@Override
	public boolean isExistFruit(long id) {
		return fruits.stream().anyMatch(fruit -> fruit.getId() == id);
	}

	@Override
	public FruitAmountReadResponse getFruitByStatus(String name) {
		long salesAmount = fruits.stream()
			.filter(fruit -> fruit.getName().equals(name) && fruit.isStatus())
			.mapToLong(Fruit::getPrice)
			.sum();

		long notSalesAmount = fruits.stream()
			.filter(fruit -> fruit.getName().equals(name) && !fruit.isStatus())
			.mapToLong(Fruit::getPrice)
			.sum();

		return new FruitAmountReadResponse(salesAmount, notSalesAmount);
	}
}
