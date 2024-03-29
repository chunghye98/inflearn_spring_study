package com.group.libraryapp.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceV2 {
	private final UserRepository userRepository;

	@Transactional
	public void saveUser(UserCreateRequest request) {
		userRepository.save(new User(request.getName(), request.getAge()));
	}

	@Transactional(readOnly = true)
	public List<UserResponse> getUsers() {
		return userRepository.findAll().stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateUser(UserUpdateRequest request) {
		User user = userRepository.findById(request.getId())
			.orElseThrow(IllegalArgumentException::new);

		user.updateName(request.getName());
	}

	@Transactional
	public void deleteUser(String name) {
		User user = userRepository.findByName(name)
			.orElseThrow(IllegalArgumentException::new);
		userRepository.delete(user);
	}
}
