package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record RegisterConcertResponse(String title, Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static RegisterConcertResponse of(Concert concert) {
		return new RegisterConcertResponse(
			concert.getTitle(),
			concert.getWriter().getId(),
			concert.getCreatedAt(),
			concert.getUpdatedAt()
		);
	}
}
