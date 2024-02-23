package com.group.libraryapp.controller.fruit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;

@RestController
public class FruitController {

	private final JdbcTemplate jdbcTemplate;

	public FruitController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostMapping("/api/v1/fruit")
	public void createFruit(@RequestBody FruitCreateRequest request) {
		String sql = "INSERT INTO fruit (name, price, stocked_date) VALUES (?, ?,?)";

		jdbcTemplate.update(sql, request.getName(), request.getPrice(), request.getWarehousingDate());
	}

	@PutMapping("/api/v1/fruit")
	public void updateFruit(@RequestBody FruitUpdateRequest request) {
		String readSql = "SELECT * FROM fruit WHERE id = ?";
		boolean isExistFruit = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
		if (isExistFruit) {
			throw new IllegalArgumentException("존재하지 않는 과일입니다.");
		}

		String sql = "UPDATE fruit SET status = ?";
		jdbcTemplate.update(sql, true);
	}

	@GetMapping("/api/v1/fruit/stat")
	public FruitAmountReadResponse getFruitByStatus(@RequestParam String name) {
		String sql = "SELECT "
			+ "	 SUM(CASE WHEN status = true THEN price ELSE 0 END) AS salesAmount,\n"
			+ "  SUM(CASE WHEN status = false THEN price ELSE 0 END) AS notSalesAmount\n"
			+ "FROM fruit\n"
			+ "WHERE name = ?";

		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			long salesAmount = rs.getLong("salesAmount");
			long notSalesAmount = rs.getLong("notSalesAmount");
			return new FruitAmountReadResponse(salesAmount, notSalesAmount);
		}, name);
	}
}
