package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.user.dto.response.UserReservationResponse;

public interface ReservationService {

	ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId);

	/**
	 * 결제 실패또는 예매를 취소할 때, 예매의 status를 CANCELLED로 변경하는 메서드
	 * @param reservationId 예매 ID
	 */
	void cancelReservation(Long reservationId);

	/**
	 * UserController /api/users/me/reservations 에서 호출
	 * @param userId 회원 ID
	 * @param pageable default
	 * @return 해당 ID의 회원(로그인한 유저)의 예매 목록들을 페이징으로 조회
	 */
	Page<UserReservationResponse> getUserReservations(Long userId, Pageable pageable);

	/**
	 *
	 * @param userId 유저 ID
	 * @param concertId 공연 ID
	 * @return Reservation 엔티티 객체
	 */
	Reservation findReservationByUserIdAndConsortId(Long userId, Long concertId);
}
