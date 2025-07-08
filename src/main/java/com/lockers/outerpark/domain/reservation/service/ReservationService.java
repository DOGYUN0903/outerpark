package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;

public interface ReservationService {

	ReservationResponse createReservation(ReservationRequest request, Long id);

	void cancelReservation(Long reservationId);

	ReservationResponse getUserReservation(Pageable pageable, Long id);

	/**
	 * reservationId의 예매가 존재하는지 true/false 반환
	 */
	boolean existsReservation(Long reservationId);
}
