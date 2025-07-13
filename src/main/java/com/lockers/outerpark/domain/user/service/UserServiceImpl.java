package com.lockers.outerpark.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.user.dto.response.UserResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.exception.UserErrorCode;
import com.lockers.outerpark.domain.user.exception.UserException;
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

		return UserResponse.of(authUser);
	}

	@Transactional(readOnly = true)
	public User getActiveUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if (user.getIsDeleted()) {
			throw new UserException(UserErrorCode.USER_ALREADY_DELETED);
		}

		return user;
	}

	@Override
	public void validateActiveUserById(Long userId) {
		if (!userRepository.existsByIdAndIsDeletedFalse(userId)) {
			throw new UserException(UserErrorCode.USER_NOT_FOUND);
		}
	}
}
