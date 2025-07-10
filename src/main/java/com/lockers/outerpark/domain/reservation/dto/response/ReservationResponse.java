package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {

	private final Long reservationId;

	private final List<Long> seatIds;

	private final Long userId;

	private final List<String> reservationNumbers;

	private final int count;

	private final ReservationStatus status;

	private final int totalAmount;

	private final ConcertInfo concertInfo;

	private final LocalDate reservedAt;

	private final LocalDate cancelledAt;

	public static ReservationResponse fromEntity(Reservation reservation) {
		return ReservationResponse.builder()
			.reservationId(reservation.getId())
			.seatIds(
				reservation.getReservationSeats().stream().map(rs -> rs.getSeat().getId()).collect(Collectors.toList()))
			.userId(reservation.getUser().getId())
			.reservationNumbers(
				// TODO: N+1 생기는지 확인 (List는 N+1가능성있지만 savedReservation 이기때문에 영속성 컨텍스트에서 가져온다면 문제없음)
				reservation.getReservationSeats().stream().map(ReservationSeat::getReservationNumber).collect(
					Collectors.toList()))
			.count(reservation.getCount())
			.status(reservation.getStatus())
			.totalAmount(reservation.getAmount())
			.concertInfo(new ConcertInfo(reservation.getConcert().getId(), reservation.getConcert().getTitle(),
				reservation.getConcert().getPerformanceDate()))
			.reservedAt(reservation.getReservedAt())
			.cancelledAt(reservation.getCancelledAt())
			.build();
	}
}
