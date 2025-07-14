package com.lockers.outerpark.domain.seat.repository.query;

import com.lockers.outerpark.domain.reservation.type.ReservationStatus;

import lombok.Getter;

@Getter
public class SeatStatusInfo {
	private final Long seatId;
	private final String seatNumber;
	private final String reservationStatus;

	public SeatStatusInfo(Long seatId, String seatNumber, ReservationStatus reservationStatus) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.reservationStatus = reservationStatus != null ? reservationStatus.name() : null;
	}

	public String getDisplayStatus() {
		if (reservationStatus == null || ReservationStatus.CANCELLED.name().equals(reservationStatus)) {
			return null;    // 예약 가능
		}
		return reservationStatus;
	}
}
