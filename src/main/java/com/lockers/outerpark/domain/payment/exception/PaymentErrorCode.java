package com.lockers.outerpark.domain.payment.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
	NOT_FOUNT_PAYMENT("잘못된 결제 정보입니다.", HttpStatus.NOT_FOUND),
	INVALID_PAYMENT_REQUEST("잘못된 결제 정보입니다.", HttpStatus.BAD_REQUEST),
	NOT_ENOUGH_BALANCE("결제 금액이 부족합니다.", HttpStatus.BAD_REQUEST),
	INSUFFICIENT_BALANCE("결제 과정에서 문제가 발생했습니다.", HttpStatus.BAD_REQUEST),
	ALREADY_CANCEL("이미 취소된 결제 내역입니다.", HttpStatus.BAD_REQUEST),
	INVALID_AMOUNT_REQUEST("결제 금액이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);

	private final String message;
	private final HttpStatus status;

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}
}
