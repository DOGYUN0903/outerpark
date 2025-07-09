package com.lockers.outerpark.domain.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/concerts/{concertId}")
	public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
		@Valid @RequestBody ReservationRequest request, @AuthenticationPrincipal Long userId,
		@PathVariable Long concertId) {
		return ResponseEntity.ok(
			ApiResponse.success("예매에 성공하였습니다.", reservationService.createReservation(request, userId, concertId)));
	}
}
