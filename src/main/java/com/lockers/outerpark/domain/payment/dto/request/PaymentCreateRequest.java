package com.lockers.outerpark.domain.payment.dto.request;

import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;
import com.lockers.outerpark.domain.reservation.entity.Reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateRequest {

	@NotBlank(message = "결제 수단은 필수입니다.")
	private String method;

	@NotNull(message = "결제 상태는 필수입니다.")
	private PaymentStatus status;

	public Payment toEntity(Reservation reservation) {
		return Payment.builder()
			.reservation(reservation)
			.totalAmount(reservation.getAmount())
			.method(this.method)
			.status(this.status)
			.build();
	}
}
