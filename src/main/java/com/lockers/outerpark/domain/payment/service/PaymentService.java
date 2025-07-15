package com.lockers.outerpark.domain.payment.service;

import com.lockers.outerpark.domain.payment.dto.request.PaymentCreateRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentCreateResponse;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.exception.PaymentException;

public interface PaymentService {
	/**
	 * 결제 정보를 저장하고 예약과 연관짓습니다.
	 *
	 * @param request 결제 요청 정보
	 * @param concertId 결제가 연결될 공연 ID
	 * @param userId 결제를 수행하는 사용자 ID
	 * @return 저장된 결제 응답 정보 {@link PaymentCreateResponse}
	 * @throws PaymentException 결제 처리 중 오류가 발생한 경우
	 */
	PaymentCreateResponse createPayment(PaymentCreateRequest request, Long concertId, Long userId);

	/**
	 * 현재 사용자의 결제 정보를 단건 조회합니다.
	 *
	 * @param userId 결제를 조회할 사용자 ID
	 * @return 해당 사용자의 결제 응답 정보 {@link PaymentResponse}
	 * @throws PaymentException 결제가 존재하지 않거나 권한이 없는 경우
	 */
	PaymentResponse getPayment(Long userId);

	/**
	 * 결제를 취소하고 예약 상태를 변경합니다.
	 *
	 * @param paymentId 취소할 결제 ID
	 * @param userId 결제 취소 요청을 하는 사용자 ID
	 * @throws PaymentException 취소 기한이 지났거나 권한이 없는 경우
	 */
	void updatePaymentCancel(Long paymentId, Long userId);
}
