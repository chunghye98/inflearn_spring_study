package com.group.libraryapp.domain.fruit;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fruit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long price;
	@Column(name = "stocked_date")
	private LocalDate warehousingDate;
	@Column(columnDefinition = "default false")
	private boolean status;

	public Fruit() {
	}

	public Fruit(long id, String name, long price, LocalDate warehousingDate, boolean status) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.warehousingDate = warehousingDate;
		this.status = status;
	}

	public Fruit(String name, long price, LocalDate warehousingDate) {
		this.name = name;
		this.price = price;
		this.warehousingDate = warehousingDate;
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

	public LocalDate getWarehousingDate() {
		return warehousingDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void updateStatus(boolean status) {
		this.status = status;
	}
}

