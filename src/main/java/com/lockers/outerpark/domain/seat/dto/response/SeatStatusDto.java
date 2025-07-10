package com.lockers.outerpark.domain.seat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatStatusDto {
	private Long seatId;
	private int seatNumber;
	private String reservationStatus;

	public String getDisplayStatus() {
		if (reservationStatus == null || "CANCELLED".equals(reservationStatus)) {
			return null;    // 예약 가능
		}
		return reservationStatus;
	}
}
