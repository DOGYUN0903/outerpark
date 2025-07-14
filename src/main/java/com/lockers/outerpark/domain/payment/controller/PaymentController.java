package com.lockers.outerpark.domain.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.payment.dto.request.PaymentCreateRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentCreateResponse;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService paymentService;

	/**
	 * 예약 ID와 결제 요청 정보를 바탕으로 결제를 생성합니다.
	 *
	 * @param userId 인증된 사용자 ID (스프링 시큐리티에서 주입)
	 * @param concertId 공연 ID
	 * @param paymentCreateRequest 결제 요청 정보 (결제 수단, 금액 등)
	 * @return 결제 생성 성공 응답 (201 Created)
	 */
	@PostMapping("/concerts/{concertId}")
	public ResponseEntity<ApiResponse<PaymentCreateResponse>> createPayment(
		@AuthenticationPrincipal Long userId,
		@PathVariable Long concertId,
		@RequestBody @Valid PaymentCreateRequest paymentCreateRequest) {

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				ApiResponse.success("결제 내역이 생성 되었습니다.",
					paymentService.createPayment(paymentCreateRequest, concertId, userId)));
	}

	/**
	 * 특정 결제 ID에 해당하는 결제 정보를 조회합니다.
	 *
	 * @param paymentId 조회할 결제 ID
	 * @return 결제 조회 성공 응답 (200 OK)
	 */
	@GetMapping("/{paymentId}")
	public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Long paymentId) {
		return ResponseEntity.ok(
			ApiResponse.success("결제 정보를 조회했습니다.", paymentService.getPayment(paymentId)));
	}

	/**
	 * 특정 결제를 취소하고 관련 예약도 처리합니다.
	 *
	 * @param userId 요청을 수행하는 사용자 ID
	 * @param paymentId 취소할 결제 ID
	 * @return 결제 취소 성공 응답 (200 OK)
	 */
	@PatchMapping("/{paymentId}")
	public ResponseEntity<ApiResponse<Void>> updatePaymentCancel(@AuthenticationPrincipal Long userId,
		@PathVariable Long paymentId) {
		paymentService.updatePaymentCancel(paymentId, userId);
		return ResponseEntity.ok(ApiResponse.success("결제가 취소되었습니다.", null));
	}
}
