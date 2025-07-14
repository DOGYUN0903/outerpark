package com.lockers.outerpark.domain.user.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.lockers.outerpark.domain.reservation.dto.response.ReservationConcertInfo;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReservationResponse {

	private final Long reservationId;

	private final ReservationConcertInfo reservationConcertInfo;

	private final int totalAmount;

	private final SeatsInfo seatsInfo;

	public record SeatsInfo(List<Long> seatIds, List<String> seatNumbers) {

	}

	public static UserReservationResponse fromEntity(Reservation reservation) {
		return UserReservationResponse.builder()
			.reservationId(reservation.getId())
			.reservationConcertInfo(new ReservationConcertInfo(
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
