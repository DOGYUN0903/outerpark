package com.lockers.outerpark.domain.seat.service;

import java.util.List;

import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatService {

	/**
	 * 전체 좌석 조회
	 */
	List<Seat> getAllSeats();

	/**
	 * 좌석 ID 유효성 검증
	 */
	Seat getSeat(Long seatId);

}