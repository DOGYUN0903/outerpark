package com.lockers.outerpark.domain.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.user.dto.response.UserReservationResponse;
import com.lockers.outerpark.domain.user.dto.response.UserResponse;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final ReservationService reservationService;

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(@AuthenticationPrincipal Long userId) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success("회원 조회를 성공적으로 하였습니다.", userService.getUserProfile(userId)));
	}

	@GetMapping("/me/reservations")
	public ResponseEntity<ApiResponse<Page<UserReservationResponse>>> getUserReservations(
		@AuthenticationPrincipal Long userId,
		Pageable pageable) {
		return ResponseEntity
			.ok(ApiResponse.success("내 예매 목록 조회에 성공하였습니다.", reservationService.getUserReservations(userId, pageable)));
	}
}
