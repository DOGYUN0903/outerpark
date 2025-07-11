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
		"AND r.concert.id = :concertId " +
		"AND rs.isDeleted = false " +
		"AND r.status != 'CANCELLED'")
	Optional<ReservationSeat> findActiveBySeatIdAndConcertId(@Param("seatId") Long seatId,
		@Param("concertId") Long concertId);

	/**
	 * DTO Projection을 활용한 성능 최적화 쿼리 (콘서트, 좌석, 예매 상태만 조회)
	 */
	@Query("SELECT new com.lockers.outerpark.domain.seat.dto.response.SeatStatusDto("
		+ "s.id, s.seatNumber, r.status) "
		+ "FROM ReservationSeat rs "
		+ "JOIN rs.seat s "
		+ "JOIN rs.reservation r "
		+ "WHERE r.concert.id = :concertId "
		+ "AND r.status != 'CANCELLED' "
		+ "AND rs.isDeleted = false")
	List<SeatStatusDto> findSeatStatusByConcertId(@Param("concertId") Long concertId);

	/**
	 * 특정 콘서트에서 특정 좌석의 활성 예약 존재 여부 확인
	 */
	@Query("SELECT COUNT(rs) > 0 FROM ReservationSeat rs "
		+ "JOIN rs.reservation r "
		+ "WHERE rs.seat.id = :seatId "
		+ "AND r.concert.id = :concertId "
		+ "AND rs.isDeleted = false "
		+ "AND r.status IN ('PENDING', 'CONFIRMED')")
	boolean existsActiveBySeatIdAndConcertId(@Param("seatId") Long seatId,
		@Param("concertId") Long concertId);

	/**
	 * 주어진 좌석 목록 중 예약 불가능한 좌석 ID들을 반환
	 * 단일 쿼리로 성능 최적화
	 */
	@Query("SELECT rs.seat.id FROM ReservationSeat rs "
		+ "JOIN rs.reservation r "
		+ "WHERE rs.seat.id IN :seatIds "
		+ "AND r.concert.id = :concertId "
		+ "AND rs.isDeleted = false "
		+ "AND r.status IN ('PENDING', 'CONFIRMED')")
	List<Long> findUnavailableSeatIds(@Param("seatIds") List<Long> seatIds,
		@Param("concertId") Long concertId);
}
