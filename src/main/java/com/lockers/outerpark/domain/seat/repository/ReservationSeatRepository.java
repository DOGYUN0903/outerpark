package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.seat.dto.response.SeatStatusDto;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {

	/**
	 * 특정 콘서트의 특정 좌석 활성 예약 조회
	 */
	@Query("SELECT rs FROM ReservationSeat rs " +
		"JOIN FETCH rs.reservation r " +
		"WHERE rs.seat.id = :seatId " +
		"AND rs.seat.concert.id = :concertId " +
		"AND rs.isDeleted = false")
	Optional<ReservationSeat> findActiveBySeatIdAndConcertId(@Param("seatId") Long seatId,
		@Param("concertId") Long concertId);

	/**
	 * DTO Projection을 활용한 성능 최적화 쿼리 (콘서트, 좌석, 예매 상태만 조회)
	 */
	@Query("SELECT new com.lockers.outerpark.domain.seat.dto.response.SeatStatusDto(" +
		"s.id, s.seatNumber, r.status) " +
		"FROM ReservationSeat rs " +
		"JOIN rs.seat s " +
		"JOIN rs.reservation r " +
		"WHERE s.concert.id = :concertId " +
		"AND r.status != 'CANCELLED' " +
		"AND rs.isDeleted = false")
	List<SeatStatusDto> findSeatStatusByConcertId(@Param("concertId") Long concertId);
}
