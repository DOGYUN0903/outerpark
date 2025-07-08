package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;

import com.lockers.outerpark.domain.concert.dto.FindConcertResponse;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertRequest;
import com.lockers.outerpark.domain.concert.dto.RegisterConcertResponse;
import com.lockers.outerpark.domain.concert.dto.UpdateConcertResponse;

public class ConcertServiceImpl implements ConcertService {
	@Override
	public RegisterConcertResponse registerConcert(Long userId, RegisterConcertRequest request) {
		return null;
	}

	@Override
	public UpdateConcertResponse updateConcert(Long userId) {
		return null;
	}

	@Override
	public FindConcertResponse findConcert(Long concertId) {
		return null;
	}

	@Override
	public Page<FindConcertResponse> findConcerts() {
		return null;
	}

	@Override
	public void deleteConcert(Long userId) {

	}
}
