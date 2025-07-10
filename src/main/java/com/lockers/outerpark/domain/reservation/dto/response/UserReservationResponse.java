package com.lockers.outerpark.domain.reservation.dto.response;

import java.util.stream.Collectors;

import com.lockers.outerpark.domain.reservation.entity.Reservation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReservationResponse {

	private final Long reservationId;

	private final ConcertInfo concertInfo;

	private final int totalAmount;

	private final SeatsInfo seatsInfo;

	// TODO: N+1 해결하기
	public static UserReservationResponse fromEntity(Reservation reservation) {
		return UserReservationResponse.builder()
			.reservationId(reservation.getId())
			.concertInfo(new ConcertInfo(
				reservation.getConcert().getId(),
				reservation.getConcert().getTitle(),
				reservation.getConcert().getPerformanceDate()))
			.totalAmount(reservation.getAmount())
			.seatsInfo(new SeatsInfo(
				reservation.getReservationSeats().stream()
					.map(rs -> rs.getSeat().getId())
					.collect(Collectors.toList()),
				reservation.getReservationSeats().stream()
					.map(rs -> rs.getSeat().getSeatNumber())
					.collect(Collectors.toList())))
			.build();
	}
}
