package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDate;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {

	private final Long reservationId;

	private final Long seatId;

	private final Long userId;

	private final String reservationNumber;

	private final ReservationStatus status;

	private final int amount;

	private final ConcertInfo concertInfo;

	private final LocalDate reservedAt;

	private final LocalDate cancelledAt;

	public static ReservationResponse fromEntity(Reservation reservation, Concert concert) {
		return ReservationResponse.builder()
			.reservationId(reservation.getId())
			.seatId(reservation.getSeat().getId())
			.userId(reservation.getUser().getId())
			.reservationNumber(reservation.getReservationNumber())
			.status(reservation.getStatus())
			.amount(reservation.getAmount())
			.concertInfo(ConcertInfo.builder()
				.title(concert.getTitle())
				.performanceDate(concert.getPerformanceDate())
				.runningTime(concert.getRunningTime())
				.limitAge(concert.getLimitAge())
				.price(concert.getPrice())
				.build())
			.reservedAt(reservation.getReservedAt())
			.cancelledAt(reservation.getCancelledAt())
			.build();
	}
}
