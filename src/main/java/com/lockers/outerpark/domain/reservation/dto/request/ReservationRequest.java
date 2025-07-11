package com.lockers.outerpark.domain.reservation.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {

	@Size(min = 1, max = 2)
	@NotNull
	private List<Long> seatIds;
}
