package com.lockers.outerpark.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	private Long reservationId;
	private int totalAmount;
	private int count;
	private String method;
	private String status;
}
