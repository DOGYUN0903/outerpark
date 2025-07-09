package com.lockers.outerpark.domain.payment.dto;

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
	private int count;
	private String method;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static PaymentResponse from(Payment savedPayment, Long reservationId) {
		return PaymentResponse.builder()
			.id(savedPayment.getId())
			.reservationId(reservationId)
			.totalAmount(savedPayment.getTotalAmount())
			.count(savedPayment.getCount())
			.method(savedPayment.getMethod())
			.status(savedPayment.getStatus())
			.createdAt(savedPayment.getCreatedAt())
			.updatedAt(savedPayment.getUpdatedAt())
			.build();
	}
}
