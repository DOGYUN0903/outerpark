package com.lockers.outerpark.domain.payment.service;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;

public interface PaymentService {

	PaymentResponse savePayment(PaymentRequest request, Long reservationId, Long userId);

	PaymentResponse findOnePayment(Long userId);

	PaymentResponse cancelPayment(Long paymentId, Long userId);
}
