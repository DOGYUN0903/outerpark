package com.lockers.outerpark.domain.user.dto.response;

import java.time.LocalDate;

import com.lockers.outerpark.domain.user.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String nickname;
	private Long balance;
	private LocalDate birth;
	private UserRole userRole;
}
