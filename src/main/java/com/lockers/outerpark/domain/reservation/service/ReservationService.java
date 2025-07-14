package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.exception.ReservationException;
import com.lockers.outerpark.domain.user.dto.response.UserReservationResponse;

public interface ReservationService {

	/**
	 * 사용자가 지정한 공연에 대해 예매를 생성합니다.
	 *
	 * @param request 예매 요청 정보
	 * @param userId 예매를 요청한 사용자 ID
	 * @param concertId 예매 대상 콘서트 ID
	 * @return 예매 결과를 담은 {@link ReservationResponse}
	 * @throws ReservationException 예매 조건이 충족되지 않거나 좌석이 유효하지 않은 경우
	 */
	ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId);

	/**
	 * 예매를 취소하거나 결제 실패 시, 해당 예매의 상태를 CANCELLED로 변경합니다.
	 *
	 * @param reservationId 취소할 예매 ID
	 * @throws ReservationException 예매가 존재하지 않거나 취소할 수 없는 상태인 경우
	 */
	void cancelReservation(Long reservationId);

	/**
	 * 특정 사용자의 예매 내역을 페이징하여 조회합니다.
	 *
	 * @param userId 조회할 사용자 ID
	 * @param pageable 페이징 정보
	 * @return 사용자의 예매 목록을 담은 {@link Page} 객체
	 * @throws ReservationException 사용자가 존재하지 않거나 예매 내역 조회에 실패한 경우
	 */
	Page<UserReservationResponse> getUserReservations(Long userId, Pageable pageable);

	/**
	 * 특정 사용자와 콘서트 ID에 해당하는 예매 정보를 조회합니다.
	 *
	 * @param userId 사용자 ID
	 * @param concertId 콘서트 ID
	 * @return 일치하는 {@link Reservation} 엔티티
	 * @throws ReservationException 예매가 존재하지 않거나 일치하는 정보가 없는 경우
	 */
	Reservation findReservationByUserIdAndConcertId(Long userId, Long concertId);
}
