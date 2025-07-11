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
import com.lockers.outerpark.domain.payment.dto.response.PaymentSaveResponse;
import com.lockers.outerpark.domain.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	/**
	 * 예약 ID와 결제 요청 정보를 바탕으로 결제를 생성합니다.
	 *
	 * @param userId 인증된 사용자 ID (스프링 시큐리티에서 주입)
	 * @param concertId 공연 ID
	 * @param paymentRequest 결제 요청 정보 (결제 수단, 금액 등)
	 * @return 결제 생성 성공 응답 (201 Created)
	 */
	@PostMapping("/api/payments/concerts/{concertId}")
	public ResponseEntity<ApiResponse<PaymentSaveResponse>> savePayment(
		@AuthenticationPrincipal Long userId,
		@PathVariable Long concertId,
		@RequestBody @Valid PaymentRequest paymentRequest) {

		return new ResponseEntity<>(
			ApiResponse.success("결제 내역이 생성 되었습니다.",
				paymentService.savePayment(paymentRequest, concertId, userId)),
			HttpStatus.CREATED);
	}

	/**
	 * 특정 결제 ID에 해당하는 결제 정보를 조회합니다.
	 *
	 * @param paymentId 조회할 결제 ID
	 * @return 결제 조회 성공 응답 (200 OK)
	 */
	@GetMapping("/api/payments/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> findOnePayment(@PathVariable Long paymentId) {
		return new ResponseEntity<>(
			ApiResponse.success("결제 정보를 조회했습니다.", paymentService.findOnePayment(paymentId)), HttpStatus.OK);
	}

	/**
	 * 특정 결제를 취소하고 관련 예약도 처리합니다.
	 *
	 * @param userId 요청을 수행하는 사용자 ID
	 * @param paymentId 취소할 결제 ID
	 * @return 결제 취소 성공 응답 (200 OK)
	 */
	@PatchMapping("/api/payments/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> cancelPayment(@AuthenticationPrincipal Long userId,
		@PathVariable Long paymentId) {
		paymentService.cancelPayment(paymentId, userId);
		return new ResponseEntity<>(
			ApiResponse.success("결제가 취소되었습니다.", null),
			HttpStatus.NO_CONTENT);
	}
}
