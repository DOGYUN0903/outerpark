package com.lockers.outerpark.domain.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.concert.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
