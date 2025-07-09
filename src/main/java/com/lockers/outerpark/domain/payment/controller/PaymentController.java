package com.lockers.outerpark.domain.payment.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.payment.dto.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.PaymentResponse;
import com.lockers.outerpark.domain.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/api/payments")
	public ApiResponse<PaymentResponse> savePaymentHistory(@AuthenticationPrincipal UserDetails userDetails,
		PaymentRequest paymentRequest) {
		Long userId = 1L;
		return ApiResponse.success("Payments Create", paymentService.savePaymentHistory(paymentRequest, userId));
	}
}
