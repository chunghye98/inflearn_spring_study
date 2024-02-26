package com.group.libraryapp.repository.fruit;

import java.time.LocalDate;

import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;

public interface FruitRepository {
	void createFruit(String name, long price, LocalDate warehousingDate);
	void updateFruit(long id);
	boolean isExistFruit(long id);
	FruitAmountReadResponse getFruitByStatus(String name);
}
