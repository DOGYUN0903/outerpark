package com.lockers.outerpark.domain.seat.service;

import java.util.List;

import com.lockers.outerpark.domain.seat.dto.response.ConcertSeatStatusResponse;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.exception.SeatException;

public interface SeatService {

	// ================= 기본 좌석 메서드 ================

	/**
	 * 특정 콘서트의 지정된 좌석이 예약 가능한지 확인합니다.
	 *
	 * @param seatId 좌석 ID
	 * @param concertId 콘서트 ID
	 * @return 예약 가능 여부 (true/false)
	 * @throws SeatException 좌석이 존재하지 않거나 콘서트에 속하지 않은 경우
	 */
	boolean isAvailable(Long seatId, Long concertId);

	/**
	 * 특정 콘서트의 지정된 좌석 상태를 조회합니다.
	 *
	 * @param seatId 좌석 ID
	 * @param concertId 콘서트 ID
	 * @return 좌석 상태 값 (PENDING, CONFIRMED, 또는 null)
	 * @throws SeatException 좌석이 존재하지 않거나 콘서트에 속하지 않은 경우
	 */
	String getSeatStatus(Long seatId, Long concertId);

	/**
	 * 특정 콘서트의 모든 좌석과 각 좌석의 상태 정보를 조회합니다.
	 *
	 * @param concertId 콘서트 ID
	 * @return 좌석 상태 정보를 담은 {@link ConcertSeatStatusResponse} 객체
	 * @throws SeatException 콘서트가 존재하지 않거나 좌석 조회에 실패한 경우
	 */
	ConcertSeatStatusResponse getSeatsForConcert(Long concertId);

	// ================= Reservation 도메인 지원 메서드 ================

	/**
	 * 예약 생성을 위해 지정된 콘서트 내 좌석을 조회합니다.
	 *
	 * @param seatId 좌석 ID
	 * @param concertId 콘서트 ID
	 * @return 예약용 {@link Seat} 객체
	 * @throws SeatException 좌석이 존재하지 않거나 예약 불가능한 경우
	 */
	Seat getSeatForReservation(Long seatId, Long concertId);

	/**
	 * 다중 좌석에 대해 예약 가능한 좌석을 일괄 조회합니다.
	 *
	 * @param seatIds 좌석 ID 목록
	 * @param concertId 콘서트 ID
	 * @return 예약 가능한 {@link Seat} 객체 리스트
	 * @throws SeatException 좌석이 유효하지 않거나 예약 불가능한 경우
	 */
	List<Seat> getSeatsForReservation(List<Long> seatIds, Long concertId);

	/**
	 * 주어진 좌석들이 모두 예약 가능한지 검증합니다.
	 *
	 * @param seatIds 좌석 ID 목록
	 * @param concertId 콘서트 ID
	 * @throws SeatException 하나 이상의 좌석이 예약 불가능한 경우
	 */
	void validateSeatsAvailability(List<Long> seatIds, Long concertId);

}