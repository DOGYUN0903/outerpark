package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

public interface ReservationService {

	ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId);

	void cancelReservation(Long reservationId);

	ReservationResponse getUserReservation(Pageable pageable, Long id);

	/**
	 * reservationId의 예매가 존재하는지 true/false 반환
	 */
	boolean existsReservation(Long reservationId);

	/**
	 *
	 * @param reservationId 예매 ID
	 * @return Reservation 엔티티 객체
	 */
	Reservation findReservationById(Long reservationId);
}
