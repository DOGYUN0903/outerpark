package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.seat.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

	// ===================== Reservation에서 사용 =========================

	/**
	 * 삭제되지 않은 좌석을 ID로 조회
	 */
	Optional<Seat> findByIdAndIsDeletedFalse(Long seatId);

	// ===================== Concert에서 사용 =========================

	/**
	 * 특정 콘서트의 모든 좌석을 soft delete 처리
	 * 콘서트 삭제 시 연관된 좌석들을 일괄 삭제할 때 사용
	 */
	@Modifying
	@Query("UPDATE Seat s SET s.isDeleted = true, s.deletedAt = CURRENT_TIMESTAMP WHERE s.concert.id = :concertId")
	void softDeleteAllByConcertId(@Param("concertId") Long concertId);

	// ===================== Seat에서 사용 =========================

	/**
	 * 특정 콘서트의 삭제되지 않은 모든 좌석을 조회
	 */
	List<Seat> findByConcertIdAndIsDeletedFalse(Long concertId);

	/**
	 * 특정 콘서트의 삭제되지 않은 좌석 개수를 조회합니다.
	 */
	long countByConcertIdAndIsDeletedFalse(Long concertId);

}
