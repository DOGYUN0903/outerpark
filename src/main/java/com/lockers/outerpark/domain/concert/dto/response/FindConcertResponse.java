package com.lockers.outerpark.domain.concert.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lockers.outerpark.domain.concert.entity.Concert;

public record FindConcertResponse(String title, Integer runningTime, Long writerId, Integer price, Integer limitAge,
								  LocalDate performanceDate, LocalDateTime updatedAt) {

	public static FindConcertResponse of(Concert concert) {
		return new FindConcertResponse(
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
