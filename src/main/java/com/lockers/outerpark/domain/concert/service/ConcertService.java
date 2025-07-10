package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.concert.dto.FindConcertResponse;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertResponse;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertRequest;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertResponse;
import com.lockers.outerpark.domain.concert.entity.Concert;

public interface ConcertService {
    RegisterConcertResponse registerConcert(Long userId,
        RegisterConcertRequest request);

    UpdateConcertResponse updateConcert(Long userId, Long concert_id, UpdateConcertRequest request);

    FindConcertResponse findConcert(Long concertId);

    Page<FindConcertResponse> findConcerts(Pageable pageable);

    void deleteConcert(Long userId, Long concert_id);

    Concert getActiveConcert(Long concertId);
}
