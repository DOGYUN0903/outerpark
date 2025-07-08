package com.lockers.outerpark.domain.seat.dto.response;

import java.util.List;

import com.lockers.outerpark.domain.seat.entity.Seat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatsStatusResponse {
	private Long concertId;
	private int totalSeats;
	private int availableSeats;
	private List<SeatResponse> seats;

	public static SeatsStatusResponse of(Long concertId, List<Seat> seats) {
		List<SeatResponse> seatResponses = seats.stream()
			.map(SeatResponse::fromEntity)
			.toList();

		int availableSeats = (int)seats.stream()
			.filter(seat -> !seat.getIsReserved())
			.count();

		return SeatsStatusResponse.builder()
			.concertId(concertId)
			.totalSeats(seats.size())
			.availableSeats(availableSeats)
			.seats(seatResponses)
			.build();
	}
}
