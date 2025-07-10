package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDate;

import lombok.Getter;

@Getter
class ConcertInfo {

	private final Long id;

	private final String title;

	private final LocalDate performanceDate;

	public ConcertInfo(Long id, String title, LocalDate performanceDate) {
		this.id = id;
		this.title = title;
		this.performanceDate = performanceDate;
	}
}
