package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
class ConcertInfo {

	private Long id;

	private String title;

	private LocalDateTime performanceDate;

	public ConcertInfo(Long id, String title, LocalDateTime performanceDate) {
		this.id = id;
		this.title = title;
		this.performanceDate = performanceDate;
	}
}
