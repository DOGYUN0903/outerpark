package com.lockers.outerpark.domain.seat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.seat.dto.response.SeatsStatusResponse;
import com.lockers.outerpark.domain.seat.service.SeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concerts")
public class SeatController {

	private final SeatService seatService;

	/**
	 * 특정 콘서트의 모든 좌석 정보와 상태를 조회합니다.
	 */
	@GetMapping("/{concertId}/seats")
	public ResponseEntity<ApiResponse<SeatsStatusResponse>> getSeatsForConcert(@PathVariable Long concertId) {
		SeatsStatusResponse response = seatService.getSeatsForConcert(concertId);
		return ResponseEntity.ok(ApiResponse.success("좌석 정보를 조회했습니다.", response));
	}
}
