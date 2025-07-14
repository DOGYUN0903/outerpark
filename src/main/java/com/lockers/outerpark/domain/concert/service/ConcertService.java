package com.lockers.outerpark.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lockers.outerpark.domain.concert.dto.request.ConcertRegisterRequest;
import com.lockers.outerpark.domain.concert.dto.request.ConcertUpdateRequest;
import com.lockers.outerpark.domain.concert.dto.response.ConcertRegisterResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertResponse;
import com.lockers.outerpark.domain.concert.dto.response.ConcertUpdateResponse;
import com.lockers.outerpark.domain.concert.entity.Concert;

public interface ConcertService {
	ConcertRegisterResponse registerConcert(Long userId,
		ConcertRegisterRequest request);

	ConcertUpdateResponse updateConcert(Long userId, Long concert_id, ConcertUpdateRequest request);

	ConcertResponse findConcert(Long concertId);

	Page<ConcertResponse> findConcerts(Pageable pageable);

	void deleteConcert(Long userId, Long concert_id);

	Concert getActiveConcert(Long concertId);
}
