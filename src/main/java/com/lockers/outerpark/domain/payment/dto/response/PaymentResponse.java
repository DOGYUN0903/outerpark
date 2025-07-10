package com.lockers.outerpark.domain.payment.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.payment.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
	private Long id;
	private Long reservationId;
	private int totalAmount;
	private String method;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static PaymentResponse from(Payment payment, Long reservationId) {
		return PaymentResponse.builder()
			.id(payment.getId())
			.reservationId(reservationId)
			.totalAmount(payment.getTotalAmount())
			.method(payment.getMethod())
			.status(String.valueOf(payment.getStatus()))
			.createdAt(payment.getCreatedAt())
			.updatedAt(payment.getUpdatedAt())
			.build();
	}
}
