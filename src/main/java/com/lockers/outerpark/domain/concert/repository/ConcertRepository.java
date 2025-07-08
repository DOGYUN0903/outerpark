package com.lockers.outerpark.domain.concert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.user.entity.User;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Optional<Concert> findConcertByWriter(User writer);
}
