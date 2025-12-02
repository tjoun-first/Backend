package com.newsmoa.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newsmoa.app.domain.User;
import com.newsmoa.app.dto.UserRequest;
import com.newsmoa.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User signup(UserRequest userRequest) {
		if (userRepository.existsById(userRequest.getId())) {
			throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
		}

		User user = new User();
		user.setId(userRequest.getId());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

		return userRepository.save(user);
	}

	// ID 중복 체크 서비스
	public boolean isIdAvailable(String id) {
		return !userRepository.existsById(id);
		// 존재하면 true → 사용 불가 → 따라서 !를 붙여 "사용 가능" 반환
	}
}
