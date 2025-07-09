package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.seat.entity.ReservationSeat;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {

	/**
	 * 특정 좌석의 현재 유효한 예약 조회
	 */
	@Query("SELECT rs FROM ReservationSeat rs "
		+ "JOIN rs.reservation r "
		+ "WHERE rs.seat.id = :seatId "
		+ "AND r.status != 'CANCELLED' "
		+ "AND rs.isDeleted = false")
	Optional<ReservationSeat> findActiveBySeatId(@Param("seatId") Long seatId);

	/**
	 * 특정 콘서트의 모든 예약된 좌석 조회
	 */
	@Query("SELECT rs FROM ReservationSeat rs "
		+ "JOIN rs.reservation r "
		+ "WHERE rs.seat.concert.id = :concertId "
		+ "AND r.status != 'CANCELLED' "
		+ "AND rs.isDeleted = false")
	List<ReservationSeat> findActiveByConcertId(@Param("concertId") Long concertId);
}
