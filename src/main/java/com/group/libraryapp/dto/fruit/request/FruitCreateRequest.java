package com.group.libraryapp.dto.fruit.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class FruitCreateRequest {
	private String name;
	private LocalDate warehousingDate;
	private long price;
}
