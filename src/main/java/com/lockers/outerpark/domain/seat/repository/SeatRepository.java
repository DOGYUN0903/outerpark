package com.lockers.outerpark.domain.seat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	// 콘서트별 좌석 조회
	List<Seat> findByConcertIdAndIsDeletedFalse(Long concertId);

	// 콘서트별 좌석 개수
	long countByConcertIdAndIsDeletedFalse(Long concertId);

	// 콘서트의 모든 좌석 조회 (Soft Delete용)
	List<Seat> findByConcertId(Long concertId);

}
