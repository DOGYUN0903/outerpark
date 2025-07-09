package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	/**
	 * 삭제되지 않은 좌석을 ID로 조회
	 */
	Optional<Seat> findByIdAndIsDeletedFalse(Long seatId);

	/**
	 * 전체 좌석 조회 (Concert Service에서 1~100번 좌석 정보 가져올 때 사용)
	 */
	List<Seat> findByIsDeletedFalse();

}
