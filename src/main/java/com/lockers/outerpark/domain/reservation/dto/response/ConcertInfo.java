package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ConcertInfo {

	private Long id;

	private String title;

	private LocalDateTime performanceDate;
}
