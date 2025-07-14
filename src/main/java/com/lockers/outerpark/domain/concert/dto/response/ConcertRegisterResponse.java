package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record ConcertRegisterResponse(String title, Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static ConcertRegisterResponse of(Concert concert) {
		return new ConcertRegisterResponse(
			concert.getTitle(),
			concert.getWriter().getId(),
			concert.getCreatedAt(),
			concert.getUpdatedAt()
		);
	}
}
