package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record ConcertResponse(String title, Integer runningTime, Long writerId, Integer price, Integer limitAge,
							  LocalDate performanceDate, LocalDateTime updatedAt) {

	public static ConcertResponse of(Concert concert) {
		return new ConcertResponse(
			concert.getTitle(),
			concert.getRunningTime(),
			concert.getWriter().getId(),
			concert.getPrice(),
			concert.getLimitAge(),
			concert.getPerformanceDate(),
			concert.getUpdatedAt()
		);
	}
}
