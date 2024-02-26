package com.group.libraryapp.domain.fruit;

import java.time.LocalDate;

public class Fruit {
	private final long id;
	private final String name;
	private final long price;
	private final LocalDate warehousingDate;
	private boolean status;

	public Fruit(long id, String name, long price, LocalDate warehousingDate, boolean status) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.warehousingDate = warehousingDate;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

