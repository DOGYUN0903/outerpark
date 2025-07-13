package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record UpdateConcertResponse(String title, Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static UpdateConcertResponse of(Concert concert) {
		return new UpdateConcertResponse(
			concert.getTitle(),
			concert.getWriter().getId(),
			concert.getCreatedAt(),
			concert.getUpdatedAt()
		);
	}
}
