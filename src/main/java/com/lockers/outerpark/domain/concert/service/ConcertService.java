package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lockers.outerpark.domain.concert.dto.FindConcertResponse;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertResponse;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertResponse;

import jakarta.validation.Valid;

public interface ConcertService {
	RegisterConcertResponse registerConcert(@AuthenticationPrincipal Long userId,
		@Valid @RequestBody RegisterConcertRequest request);

	UpdateConcertResponse updateConcert(@AuthenticationPrincipal Long userId);

	FindConcertResponse findConcert(@RequestParam Long concertId);

	Page<FindConcertResponse> findConcerts();

	void deleteConcert(@AuthenticationPrincipal Long userId);
}
