package com.lockers.outerpark.domain.user.service;

import com.lockers.outerpark.domain.user.dto.response.UserResponse;
import com.lockers.outerpark.domain.user.entity.User;

public interface UserService {

	UserResponse getUserProfile(Long userId);

	User getActiveUserById(Long userId);
}
