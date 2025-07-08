package com.lockers.outerpark.domain.reservation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.domain.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	// @PostMapping
	// public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
	// 	@Valid @RequestBody ReservationRequest request, @AuthenticationPrincipal Long id) {
	// 	reservationService.createReservation(request, id);
	// }
}
