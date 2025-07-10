package com.lockers.outerpark.domain.reservation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByIdAndStatus(Long id, ReservationStatus status);

	Optional<Reservation> findByIdAndStatusNot(Long reservationId, ReservationStatus reservationStatus);

	Page<Reservation> findByUserId(Long userId, Pageable pageable);
}
