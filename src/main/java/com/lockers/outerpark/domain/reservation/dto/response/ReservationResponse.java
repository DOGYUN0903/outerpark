package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.seat.entity.Seat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {

	private final Long reservationId;

	private final List<Long> seatId;

	private final Long userId;

	private final List<String> reservationNumber;

	private final ReservationStatus status;

	private final int amount;

	private final ConcertInfo concertInfo;

	private final LocalDate reservedAt;

	private final LocalDate cancelledAt;

	public static ReservationResponse fromEntity(Reservation reservation, List<Seat> seats) {
		return ReservationResponse.builder()
			.reservationId(reservation.getId())
			.seatId(seats.stream().map(Seat::getId).collect(Collectors.toList()))
			.userId(reservation.getUser().getId())
			.reservationNumber(
				// TODO: N+1 생기는지 확인 (List이기때문에 N+1가능성있지만 savedReservation이기때문에 영속성 컨텍스트에서 가져온다면 문제없음)
				reservation.getReservationSeats().stream().map(ReservationSeat::getReservationNumber).collect(
					Collectors.toList()))
			.status(reservation.getStatus())
			.amount(reservation.getAmount())
			.concertInfo(new ConcertInfo(reservation.getConcert().getId(), reservation.getConcert().getTitle(),
				reservation.getConcert().getPerformanceDate()))
			.reservedAt(reservation.getReservedAt())
			.cancelledAt(reservation.getCancelledAt())
			.build();
	}
}
