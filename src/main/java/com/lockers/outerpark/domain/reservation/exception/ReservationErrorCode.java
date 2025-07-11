package com.lockers.outerpark.domain.reservation.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

public enum ReservationErrorCode implements ErrorCode {
	NOT_FOUND(HttpStatus.NOT_FOUND, "해당 예매가 존재하지 않습니다."),
	INVALID_RESERVATION_STATE(HttpStatus.NOT_FOUND, "예매 상태가 유효하지 않습니다.");

	private final HttpStatus status;
	private final String message;

	ReservationErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}
}
