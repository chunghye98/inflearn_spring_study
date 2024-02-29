package com.group.libraryapp.dto.fruit.response;

import java.time.LocalDate;

import com.group.libraryapp.domain.fruit.Fruit;

public class FruitResponse {
	private String name;
	private long price;
	private LocalDate warehousingDate;

	public FruitResponse(Fruit fruit) {
		this.name = fruit.getName();
		this.price = fruit.getPrice();
		this.warehousingDate = fruit.getWarehousingDate();
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}

	public LocalDate getWarehousingDate() {
		return warehousingDate;
	}
}
