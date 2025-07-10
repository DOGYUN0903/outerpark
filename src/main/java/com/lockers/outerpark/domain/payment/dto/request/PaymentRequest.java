package com.lockers.outerpark.domain.payment.dto.request;

import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	@Positive
	private int totalAmount;
	private String method;
	private String status;

	public Payment toEntity(Reservation reservation) {
		return Payment.builder()
			.reservation(reservation)
			.totalAmount(this.totalAmount)
			.method(this.method)
			.status(this.status)
			.build();
	}
}
