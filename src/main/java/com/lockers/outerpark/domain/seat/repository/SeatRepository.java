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

}
