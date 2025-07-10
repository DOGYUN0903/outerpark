package com.lockers.outerpark.domain.concert.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lockers.outerpark.domain.concert.entity.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Optional<Concert> findByIdAndIsDeletedFalse(Long id);

    Page<Concert> findAllByIsDeletedFalse(Pageable pageable);
}
