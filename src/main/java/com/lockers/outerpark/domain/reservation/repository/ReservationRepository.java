package com.lockers.outerpark.domain.reservation.repository;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
