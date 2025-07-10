package com.lockers.outerpark.domain.seat.service;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.seat.dto.response.SeatResponse;

public interface SeatService {

	// =========== Reservation 필요 메서드 =============

	// 좌석 예약 처리
	void reserveSeat(Long seatId);

	// =========== Reservation & Payment 필요 메서드 =============

	// 좌석 예약 취소
	void cancelSeatReservation(Long seatId);

	// 좌석 정보 조회 (단건)
	SeatResponse getSeat(Long seatId); // ResponseDto로 수정

	// =========== Concert 필요 메서드 =============

	void createSeatsForConcert(Concert concert, int seatCount);

	// Concert 삭제 시 좌석도 삭제
	void deleteAllSeatsByConcert(Long concertId);

}