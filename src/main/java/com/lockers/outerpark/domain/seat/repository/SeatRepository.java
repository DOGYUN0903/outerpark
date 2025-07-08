package com.lockers.outerpark.domain.seat.repository;

import com.lockers.outerpark.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
