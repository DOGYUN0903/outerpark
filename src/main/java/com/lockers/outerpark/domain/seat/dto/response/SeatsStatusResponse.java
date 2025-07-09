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
	private int reservedSeats;
	private List<SeatResponse> seats;

	/**
	 * 좌석 목록으로부터 SeatsStatusResponse 생성 (기본상태)
	 */
	public static SeatsStatusResponse of(Long concertId, List<Seat> seats) {
		List<SeatResponse> seatResponses = seats.stream()
			.map(SeatResponse::fromEntity)
			.toList();

		return SeatsStatusResponse.builder()
			.concertId(concertId)
			.totalSeats(seats.size())
			.availableSeats(seats.size())
			.reservedSeats(0)
			.seats(seatResponses)
			.build();
	}

	/**
	 * 좌석 상태와 함께 SeatsStatusResponse 생성
	 */
	public static SeatsStatusResponse ofWithStatus(Long concertId, List<SeatResponse> seatResponses) {
		// 상태별 좌석 개수 계산
		int availableCount = (int)seatResponses.stream()
			.filter(seat -> "AVAILABLE".equals(seat.getStatus()))
			.count();

		int reservedCount = (int)seatResponses.stream()
			.filter(seat -> "RESERVED".equals(seat.getStatus()) || "PENDING".equals(seat.getStatus()))
			.count();

		return SeatsStatusResponse.builder()
			.concertId(concertId)
			.totalSeats(seatResponses.size())
			.availableSeats(availableCount)
			.reservedSeats(reservedCount)
			.seats(seatResponses)
			.build();
	}
}
