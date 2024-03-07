package com.group.libraryapp.dto.fruit.response;

import java.time.LocalDate;

import com.group.libraryapp.domain.fruit.Fruit;

import lombok.Getter;

@Getter
public class FruitResponse {
	private String name;
	private long price;
	private LocalDate warehousingDate;

	public FruitResponse(Fruit fruit) {
		this.name = fruit.getName();
		this.price = fruit.getPrice();
		this.warehousingDate = fruit.getWarehousingDate();
	}
}
