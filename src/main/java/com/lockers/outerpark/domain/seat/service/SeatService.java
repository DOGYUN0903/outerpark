package com.lockers.outerpark.domain.seat.service;

import com.lockers.outerpark.domain.seat.dto.response.SeatsStatusResponse;

public interface SeatService {

	// ================= 기본  좌석 메서드 ================

	/**
	 * 좌석 예약 가능 여부 확인
	 */
	boolean isAvailable(Long seatId);

	/**
	 * 좌석 상태 확인 (AVAILABLE / PENDING / RESERVED)
	 */
	String getSeatStatus(Long seatId);

	/**
	 * 특정 콘서트의 모든 좌석과 상태 조회
	 */
	SeatsStatusResponse getSeatsForConcert(Long concertId);

}