package com.group.libraryapp.dto.calculator.response;

public class DateResponse {
	private String dayOfTheWeek;

	public DateResponse(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}
}
