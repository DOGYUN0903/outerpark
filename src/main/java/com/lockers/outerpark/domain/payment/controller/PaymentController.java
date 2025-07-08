package com.lockers.outerpark.domain.payment.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.domain.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private PaymentService paymentService;
}
