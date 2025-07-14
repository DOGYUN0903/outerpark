package com.lockers.outerpark.domain.concert.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ConcertUpdateRequest {

	private String title;

	private Integer runningTime;

	private Integer price;

	private Integer limitAge;

	private LocalDate performanceDate;
}
