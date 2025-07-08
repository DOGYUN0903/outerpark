package com.lockers.outerpark.domain.payment.service;

import org.springframework.stereotype.Service;

import com.lockers.outerpark.domain.payment.dto.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.PaymentResponse;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;

	@Override
	public PaymentResponse savePaymentHistory(PaymentRequest request) {
		return null;
	}
}
