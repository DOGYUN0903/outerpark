package com.lockers.outerpark.domain.payment.service;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;

public interface PaymentService {

	//결제 이력 저장
	PaymentResponse savePaymentHistory(PaymentRequest request, Long userId);

	PaymentResponse findOnePaymentHistory(Long userId);

	PaymentResponse cancelPaymentHistory(Long paymentId, Long userId);
}
