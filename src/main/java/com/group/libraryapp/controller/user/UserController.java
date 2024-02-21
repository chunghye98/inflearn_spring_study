package com.group.libraryapp.controller.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

@RestController
public class UserController {

	private final JdbcTemplate jdbcTemplate;

	public UserController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 유저 생성 API
	 * @param request
	 */
	@PostMapping("/user") // POST /user
	public void saveUser(@RequestBody UserCreateRequest request) {
		String sql = "INSERT INTO user (name, age) VALUES (?,?)";
		jdbcTemplate.update(sql, request.getName(), request.getAge());
	}

	/**
	 * 유저 조회 API
	 */
	@GetMapping("/user")
	public List<UserResponse> getUsers() {
		String sql = "SELECT * FROM user";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			long id = rs.getLong("id");
			String name = rs.getNString("name");
			int age = rs.getInt("age");
			return new UserResponse(id, name, age);
		});
	}
}
