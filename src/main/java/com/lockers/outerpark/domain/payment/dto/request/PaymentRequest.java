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

	private Long reservationId;

	@Positive
	private int totalAmount;

	private String method;
	private String status;

	public Payment toEntity(Reservation reservation) {
		return new Payment(reservation, this.totalAmount, this.method, this.status);
	}
}
