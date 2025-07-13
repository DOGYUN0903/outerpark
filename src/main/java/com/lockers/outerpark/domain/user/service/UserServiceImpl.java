package com.lockers.outerpark.domain.user.service;

import static com.lockers.outerpark.domain.user.exception.UserException.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.user.dto.response.UserResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserResponse getUserProfile(Long userId) {
		// 인증된 유저 찾기
		User authUser = getActiveUserById(userId);

		return new UserResponse(
			authUser.getId(),
			authUser.getNickname(),
			authUser.getBalance(),
			authUser.getBirth(),
			authUser.getUserRole()
		);
	}

	@Transactional(readOnly = true)
	public User getActiveUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);

		if (user.getIsDeleted()) {
			throw new UserDeletedException();
		}

		return user;
	}
}
