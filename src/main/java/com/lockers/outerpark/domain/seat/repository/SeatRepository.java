package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	// ================ 기본 조회 메서드 ==================

	/**
	 * 삭제되지 않은 좌석을 ID로 조회
	 */
	Optional<Seat> findByIdAndIsDeletedFalse(Long seatId);

	/**
	 * 전체 좌석 조회
	 */
	List<Seat> findByIsDeletedFalse();

	// ============= Concert 좌석 조회 =====================

	/**
	 * 특정 콘서트의 삭제되지 않은 모든 좌석 조회
	 */
	List<Seat> findByConcertIdAndIsDeletedFalse(Long concertId);

	/**
	 * 특정 콘서트의 삭제되지 않은 모든 좌석 개수 조회
	 */
	long countByConcertIdAndIsDeletedFalse(Long concertId);

	/**
	 * 특정 콘서트의 좌석을 좌석 번호 순으로 조회
	 */
	List<Seat> findByConcertIdAndIsDeletedFalseOrderBySeatNumber(Long concertId);

	/**
	 * 해당 좌석의 유무를 확인
	 */
	boolean existsByIdAndIsDeletedFalse(Long seatId);

}
