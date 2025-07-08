package com.lockers.outerpark.domain.payment.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
	;

	@Override
	public String getMessage() {
		return "";
	}

	@Override
	public HttpStatus getStatus() {
		return null;
	}
}
