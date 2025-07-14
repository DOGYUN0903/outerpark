package com.lockers.outerpark.domain.seat.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatStatusResponse {
	private Long concertId;
	private int totalSeats;
	private int availableSeats;
	private int reservedSeats;
	private List<SeatStatusResponse> seats;

	/**
	 * 계산된 값들과 함께 SeatsStatusResponse 생성
	 */
	public static ConcertSeatStatusResponse of(Long concertId, int totalSeats, int availableSeats,
		int reservedSeats, List<SeatStatusResponse> seats) {
		return ConcertSeatStatusResponse.builder()
			.concertId(concertId)
			.totalSeats(totalSeats)
			.availableSeats(availableSeats)
			.reservedSeats(reservedSeats)
			.seats(seats)
			.build();
	}
}
