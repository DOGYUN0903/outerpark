package com.lockers.outerpark.domain.payment.dto.response;

import java.time.LocalDateTime;

import com.lockers.outerpark.domain.payment.entity.Payment;

public record PaymentResponse(
	Long id,
	Long reservationId,
	int totalAmount,
	String method,
	String status,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static PaymentResponse from(Payment payment, Long reservationId) {
		return new PaymentResponse(
			payment.getId(),
			reservationId,
			payment.getTotalAmount(),
			payment.getMethod(),
			String.valueOf(payment.getStatus()),
			payment.getCreatedAt(),
			payment.getUpdatedAt()
		);
	}
}