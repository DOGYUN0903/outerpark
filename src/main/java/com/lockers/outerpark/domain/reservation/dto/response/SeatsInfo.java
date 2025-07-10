package com.lockers.outerpark.domain.reservation.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SeatsInfo {

	private final List<Long> seatId;

	private final List<String> seatNumber;

	public SeatsInfo(List<Long> seatId, List<String> seatNumber) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
	}
}
