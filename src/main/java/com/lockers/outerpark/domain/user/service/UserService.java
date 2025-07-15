package com.lockers.outerpark.domain.user.service;

import com.lockers.outerpark.domain.user.dto.response.UserResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.exception.UserException;

public interface UserService {

	/**
	 * 주어진 사용자 ID를 기반으로 사용자 프로필 정보를 조회합니다.
	 *
	 * @param userId 조회할 사용자의 ID
	 * @return 사용자 정보를 담은 {@link UserResponse} 객체
	 * @throws UserException 사용자가 존재하지 않거나 비활성 상태인 경우
	 */
	UserResponse getUserProfile(Long userId);

	/**
	 * 주어진 사용자 ID에 해당하는 활성화된 사용자 엔티티를 조회합니다.
	 *
	 * @param userId 조회할 사용자의 ID
	 * @return 활성 상태의 {@link User} 엔티티
	 * @throws UserException 사용자가 존재하지 않거나 비활성 상태인 경우
	 */
	User getActiveUserById(Long userId);

	/**
	 * 주어진 사용자 ID가 존재하고 활성 상태인지 유효성을 검사합니다.
	 *
	 * @param userId 검사할 사용자 ID
	 * @throws UserException 사용자가 존재하지 않거나 비활성 상태인 경우
	 */
	void validateActiveUserById(Long userId);
}
