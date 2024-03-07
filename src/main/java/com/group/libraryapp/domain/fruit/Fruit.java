package com.group.libraryapp.domain.fruit;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void updateStatus(boolean status) {
		this.status = status;
	}
}

