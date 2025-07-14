package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record ConcertUpdateResponse(String title, Long writerId, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static ConcertUpdateResponse of(Concert concert) {
		return new ConcertUpdateResponse(
			concert.getTitle(),
			concert.getWriter().getId(),
			concert.getCreatedAt(),
			concert.getUpdatedAt()
		);
	}
}
