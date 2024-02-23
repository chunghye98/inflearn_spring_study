package com.group.libraryapp.controller.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
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

	/**
	 * 유저 업데이트 API
	 * @param request
	 */
	@PutMapping("/user")
	public void updateUser(@RequestBody UserUpdateRequest request) {
		String readSql = "SELECT * FROM user WHERE id = ?";
		// 결과가 하나라도 있다면 0, 존재하지 않는다면 빈 리스트 반환
		boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
		if (isUserNotExist) {
			throw new IllegalArgumentException();
		}

		String sql = "UPDATE user SET name = ? WHERE id = ?";
		jdbcTemplate.update(sql, request.getName(), request.getId());
	}

	/**
	 * 유저 삭제 API
	 * @param name
	 */
	@DeleteMapping("/user")
	public void deleteUser(@RequestParam String name) {
		String readSql = "SELECT * FROM user WHERE id = ?";
		// 결과가 하나라도 있다면 0, 존재하지 않는다면 빈 리스트 반환
		boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
		if (isUserNotExist) {
			throw new IllegalArgumentException();
		}

		String sql = "DELETE FROM user WHERE name = ?";
		jdbcTemplate.update(sql, name);
	}
}
