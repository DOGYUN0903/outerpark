package com.lockers.outerpark.domain.payment.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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

	@PostMapping("/api/payments")
	public ApiResponse<PaymentResponse> savePaymentHistory(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody @Valid PaymentRequest paymentRequest) {

		Long userId = 1L; //todo : user 정보 얻는 로직 추가 후 삭제 예정
		return ApiResponse.success("Payments Create", paymentService.savePaymentHistory(paymentRequest, userId));
	}

	@PostMapping("/api/payments/{paymentId}")
	public ApiResponse<PaymentResponse> findOnePaymentHistory(@PathVariable Long paymentId) {
		return ApiResponse.success("Payments Create", paymentService.findOnePaymentHistory(paymentId));
	}

	@PostMapping("/api/payments/{paymentId}")
	public ApiResponse<PaymentResponse> cancelPaymentHistory(@PathVariable Long paymentId) {
		Long userId = 1L; //todo : user 정보 얻는 로직 추가 후 삭제 예정
		return ApiResponse.success("Payments Create", paymentService.cancelPaymentHistory(paymentId, userId));
	}
}
