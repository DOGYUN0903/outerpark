package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;

import com.lockers.outerpark.domain.concert.dto.FindConcertResponse;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertResponse;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertResponse;

public interface ConcertService {
	RegisterConcertResponse registerConcert(Long userId,
		RegisterConcertRequest request);

	UpdateConcertResponse updateConcert(Long userId);

	FindConcertResponse findConcert(Long concertId);

	Page<FindConcertResponse> findConcerts();

	void deleteConcert(Long userId);
}
