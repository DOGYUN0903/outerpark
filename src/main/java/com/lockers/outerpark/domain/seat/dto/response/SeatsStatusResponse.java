package com.lockers.outerpark.domain.seat.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatsStatusResponse {
	private Long concertId;
	private Long totalSeats;
	private Long availableSeats;
	private List<SeatResponse> seats;
}
