package com.lockers.outerpark.domain.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/api/payments/reservations/{reservationId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> savePaymentHistory(
		@AuthenticationPrincipal Long userId,
		@PathVariable Long reservationId,
		@RequestBody @Valid PaymentRequest paymentRequest) {

		return new ResponseEntity<>(
			ApiResponse.success("Payments Create",
				paymentService.savePaymentHistory(paymentRequest, reservationId, userId)),
			HttpStatus.CREATED);
	}

	@GetMapping("/api/payments/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> findOnePaymentHistory(@PathVariable Long paymentId) {
		return new ResponseEntity<>(
			ApiResponse.success("Payments Create", paymentService.findOnePaymentHistory(paymentId)), HttpStatus.OK);
	}

	@PatchMapping("/api/payments/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> cancelPaymentHistory(@AuthenticationPrincipal Long userId,
		@PathVariable Long paymentId) {
		return new ResponseEntity<>(
			ApiResponse.success("Payments Create", paymentService.cancelPaymentHistory(paymentId, userId)),
			HttpStatus.OK);
	}
}
