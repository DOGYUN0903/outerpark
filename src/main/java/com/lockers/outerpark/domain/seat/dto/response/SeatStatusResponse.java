package com.lockers.outerpark.domain.seat.dto.response;

import com.lockers.outerpark.domain.seat.entity.Seat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatStatusResponse {
	private Long seatId;
	private String seatNumber;
	private String status;

	/**
	 * 좌석 상태와 함께 SeatResponse 생성
	 */
	public static SeatStatusResponse fromEntityWithStatus(Seat seat, String status) {
		return SeatStatusResponse.builder()
			.seatId(seat.getId())
			.seatNumber(seat.getSeatNumber())
			.status(status)
			.build();
	}
}
