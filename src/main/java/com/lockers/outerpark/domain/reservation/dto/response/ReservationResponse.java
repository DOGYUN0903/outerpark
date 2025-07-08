package com.lockers.outerpark.domain.reservation.dto.response;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static ReservationResponse fromEntity(Reservation reservation, String concertTitle, LocalDateTime concertDate) {
        return ReservationResponse.builder()
                .reservationId(reservation.getId())
//                .seatId(reservation.getSeat().getId())
                .userId(reservation.getUser().getId())
                .reservationNumber(reservation.getReservationNumber())
                .status(reservation.getStatus())
                .amount(reservation.getAmount())
                .concertInfo(ConcertInfo.builder()
                        .concertTitle(concertTitle)
                        .concertDate(concertDate)
                        .build())
                .reservedAt(reservation.getReservedAt())
                .cancelledAt(reservation.getCancelledAt())
                .build();
    }
}
