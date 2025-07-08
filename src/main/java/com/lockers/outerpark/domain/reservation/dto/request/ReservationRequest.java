package com.lockers.outerpark.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    @NotNull
    private Long seatId;

    @NotNull
    private int amount;
}
