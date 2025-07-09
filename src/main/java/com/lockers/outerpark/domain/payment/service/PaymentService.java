package com.lockers.outerpark.domain.payment.service;

import com.lockers.outerpark.domain.payment.dto.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.PaymentResponse;

public interface PaymentService {

	//결제 이력 저장
	PaymentResponse savePaymentHistory(PaymentRequest request, Long userId);
}
