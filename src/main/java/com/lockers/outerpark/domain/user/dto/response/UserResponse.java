package com.lockers.outerpark.domain.user.dto.response;

import java.time.LocalDate;

import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;

public record UserResponse(
	Long id,
	String nickname,
	Long balance,
	LocalDate birth,
	UserRole userRole
) {
	public static UserResponse of(User user) {
		return new UserResponse(
			user.getId(),
			user.getNickname(),
			user.getBalance(),
			user.getBirth(),
			user.getUserRole()
		);
	}
}
