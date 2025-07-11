package com.lockers.outerpark.domain.reservation.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
class SeatsInfo {

	private final List<Long> seatIds;

	private final List<String> seatNumbers;

	public SeatsInfo(List<Long> seatIds, List<String> seatNumbers) {
		this.seatIds = seatIds;
		this.seatNumbers = seatNumbers;
	}
}
