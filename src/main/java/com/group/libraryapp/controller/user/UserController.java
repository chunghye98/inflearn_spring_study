package com.group.libraryapp.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

@RestController
public class UserController {

	private final List<User> users = new ArrayList<>();

	/**
	 * 유저 생성 API
	 * @param request
	 */
	@PostMapping("/user") // POST /user
	public void saveUser(@RequestBody UserCreateRequest request) {
		users.add(new User(request.getName(), request.getAge()));
	}

	/**
	 * 유저 조회 API
	 */
	@GetMapping("/user")
	public List<UserResponse> getUsers() {
		List<UserResponse> responses = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			responses.add(new UserResponse(i + 1, users.get(i)));
		}
		return responses;
	}
}
