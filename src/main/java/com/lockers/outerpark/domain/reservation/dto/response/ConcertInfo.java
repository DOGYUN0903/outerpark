package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ConcertInfo {

	private String title;

	private LocalDateTime performanceDate;

	private int runningTime;

	private int limitAge;

	private int price;
}
