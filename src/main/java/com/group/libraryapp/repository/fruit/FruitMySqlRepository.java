package com.group.libraryapp.repository.fruit;

import java.time.LocalDate;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.group.libraryapp.dto.fruit.response.FruitAmountReadResponse;

@Primary
@Repository
public class FruitMySqlRepository implements FruitRepository {

	private final JdbcTemplate jdbcTemplate;

	public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createFruit(String name, long price, LocalDate warehousingDate) {
		String sql = "INSERT INTO fruit (name, price, stocked_date) VALUES (?, ?,?)";

		jdbcTemplate.update(sql, name, price, warehousingDate);
	}

	@Override
	public void updateFruit(long id) {
		String sql = "UPDATE fruit SET status = ? WHERE id = ?";
		jdbcTemplate.update(sql, true, id);
	}

	@Override
	public boolean isExistFruit(long id) {
		String readSql = "SELECT * FROM fruit WHERE id = ?";
		return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
	}

	@Override
	public FruitAmountReadResponse getFruitByStatus(String name) {
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
