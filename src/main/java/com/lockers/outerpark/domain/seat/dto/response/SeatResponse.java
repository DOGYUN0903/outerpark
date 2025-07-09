package com.lockers.outerpark.domain.seat.dto.response;

import com.lockers.outerpark.domain.seat.entity.Seat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatResponse {
	private Long seatId;
	private int seatNumber;
	private String status;

	/**
	 * Seat 엔티티로부터 SeatResponse 생성
	 */
	public static SeatResponse fromEntity(Seat seat) {
		return SeatResponse.builder()
			.seatId(seat.getId())
			.seatNumber(seat.getSeatNumber())
			.status("AVAILABLE")
			.build();
	}

	/**
	 * 좌석 상태와 함께 SeatResponse 생성
	 */
	public static SeatResponse fromEntityWithStatus(Seat seat, String status) {
		return SeatResponse.builder()
			.seatId(seat.getId())
			.seatNumber(seat.getSeatNumber())
			.status(status)
			.build();
	}
}
