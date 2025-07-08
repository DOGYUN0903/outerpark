package com.lockers.outerpark.domain.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
	private Long id;
	private Long reservationId;
	private int totalAmount;
	private int count;
	private String method;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
