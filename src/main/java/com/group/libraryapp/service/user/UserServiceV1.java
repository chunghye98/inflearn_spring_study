package com.group.libraryapp.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;

@Service
public class UserServiceV1 {

	private final UserJdbcRepository userRepository;

	public UserServiceV1(UserJdbcRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(UserCreateRequest request) {
		userRepository.saveUser(request.getName(), request.getAge());
	}

	public List<UserResponse> getUsers() {
		return userRepository.getUsers();
	}

	public void updateUser(UserUpdateRequest request) {
		// 결과가 하나라도 있다면 0, 존재하지 않는다면 빈 리스트 반환
		boolean isUserNotExist = userRepository.isUserNotExist(request.getId());
		if (isUserNotExist) {
			throw new IllegalArgumentException();
		}
		userRepository.updateUserName(request.getName(), request.getId());
	}

	public void deleteUser(String name) {
		// 결과가 하나라도 있다면 0, 존재하지 않는다면 빈 리스트 반환
		boolean isUserNotExist = userRepository.isUserNotExist(name);
		if (isUserNotExist) {
			throw new IllegalArgumentException();
		}
		userRepository.deleteUser(name);
	}
}
