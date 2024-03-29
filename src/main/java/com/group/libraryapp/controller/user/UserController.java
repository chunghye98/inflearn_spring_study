package com.group.libraryapp.controller.user;

import java.util.List;

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
import com.group.libraryapp.service.user.UserServiceV2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserServiceV2 userService;

	/**
	 * 유저 생성 API
	 * @param request
	 */
	@PostMapping("/user") // POST /user
	public void saveUser(@RequestBody UserCreateRequest request) {
		userService.saveUser(request);
	}

	/**
	 * 유저 조회 API
	 */
	@GetMapping("/user")
	public List<UserResponse> getUsers() {
		return userService.getUsers();
	}

	/**
	 * 유저 업데이트 API
	 * @param request
	 */
	@PutMapping("/user")
	public void updateUser(@RequestBody UserUpdateRequest request) {
		userService.updateUser(request);
	}

	/**
	 * 유저 삭제 API
	 * @param name
	 */
	@DeleteMapping("/user")
	public void deleteUser(@RequestParam String name) {
		userService.deleteUser(name);
	}
}
