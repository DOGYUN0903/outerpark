package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

public interface ReservationService {

	ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId);

	/**
	 * 결제 실패또는 예매를 취소할 때, 예매의 status를 CANCELLED로 변경하는 메서드
	 * @param reservationId 예매 ID
	 */
	void cancelReservation(Long reservationId);

	/**
	 * 결제가 성공적으로 완료 후, 예매의 status를 CONFIRMED로 변경하는 메서드
	 * @param reservationId 예매 ID
	 */
	void confirmReservation(Long reservationId);

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
