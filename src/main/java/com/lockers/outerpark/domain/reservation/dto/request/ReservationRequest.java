package com.lockers.outerpark.domain.reservation.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {

	@NotNull
	private List<Long> seatIds;
}
