package com.lockers.outerpark.domain.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lockers.outerpark.domain.seat.entity.Seat;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    /**
     * 삭제되지 않은 좌석을 ID로 조회
     */
    Optional<Seat> findByIdAndIsDeletedFalse(Long seatId);

    /**
     * 전체 활성 좌석 목록 조회 (좌석 번호 순)
     */
    @Query("SELECT s FROM Seat s WHERE s.isDeleted = false ORDER BY s.seatNumber")
    List<Seat> findAllActiveSeatsOrderBySeatNumber();

    /**
     * 좌석 ID 존재 여부 확인
     */
    boolean existsByIdAndIsDeletedFalse(Long seatId);

    /**
     * 여러 좌석 ID로 일괄 조회 (예약 생성 시 사용)
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id IN :seatIds AND s.isDeleted = false")
    List<Seat> findAllByIdsAndIsDeletedFalse(@Param("seatIds") List<Long> seatIds);
}
