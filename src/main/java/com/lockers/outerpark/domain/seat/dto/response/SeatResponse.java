package com.lockers.outerpark.domain.seat.dto.response;

import com.lockers.outerpark.domain.seat.entity.Seat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatResponse {
	private Long seatId;
	private int seatNumber;
	private Boolean isReserved;

	public static SeatResponse fromEntity(Seat seat) {
		return SeatResponse.builder()
			.seatId(seat.getId())
			.seatNumber(seat.getSeatNumber())
			.isReserved(seat.getIsReserved())
			.build();
	}
}
