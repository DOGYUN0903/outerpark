package com.lockers.outerpark.domain.seat.dto.response;

import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;

import lombok.Getter;

@Getter
public class SeatStatusDto {
	private Long seatId;
	private String seatNumber;
	private String reservationStatus;

	public SeatStatusDto(Long seatId, String seatNumber, String reservationStatus) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.reservationStatus = reservationStatus;
	}

	public SeatStatusDto(Long seatId, String seatNumber, ReservationStatus reservationStatus) {
		this.seatId = seatId;
		this.seatNumber = seatNumber;
		this.reservationStatus = reservationStatus != null ? reservationStatus.name() : null;
	}

	public String getDisplayStatus() {
		if (reservationStatus == null || "CANCELLED".equals(reservationStatus)) {
			return null;    // 예약 가능
		}
		return reservationStatus;
	}
}
