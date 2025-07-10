package com.lockers.outerpark.domain.payment.service;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;

public interface PaymentService {

	PaymentResponse savePaymentHistory(PaymentRequest request, Long reservationId, Long userId);

	PaymentResponse findOnePaymentHistory(Long userId);

	PaymentResponse cancelPaymentHistory(Long paymentId, Long userId);
}
