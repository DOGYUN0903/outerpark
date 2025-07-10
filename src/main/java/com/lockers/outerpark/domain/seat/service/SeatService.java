package com.lockers.outerpark.domain.seat.service;

import com.lockers.outerpark.domain.seat.dto.response.SeatsStatusResponse;
import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatService {

	// ================= 기본 좌석 메서드 ================

	/**
	 * 특정 콘서트의 좌석 예약 가능 여부 확인
	 */
	boolean isAvailable(Long seatId, Long concertId);

	/**
	 * 특정 콘서트의 좌석 상태 확인 (PENDING / CONFIRMED / null)
	 */
	String getSeatStatus(Long seatId, Long concertId);

	/**
	 * 특정 콘서트의 모든 좌석과 상태 조회
	 */
	SeatsStatusResponse getSeatsForConcert(Long concertId);

	// ================= Reservation 도메인 지원 메서드 ================

	/**
	 * ReservationSeat 생성을 위한 Seat 객체 조회
	 */
	Seat getSeatForReservation(Long seatId, Long concertId);

}