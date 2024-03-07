package com.group.libraryapp.dto.fruit.response;

import lombok.Getter;

@Getter
public class FruitAmountReadResponse {
	private long salesAmount;
	private long notSalesAmount;

	public FruitAmountReadResponse(long salesAmount, long notSalesAmount) {
		this.salesAmount = salesAmount;
		this.notSalesAmount = notSalesAmount;
	}
}
