package com.lockers.outerpark.common.lock.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LockErrorCode implements ErrorCode {
	SEAT_LOCK_CONFLICT("이미 예약 처리 중인 좌석이 있습니다.", HttpStatus.CONFLICT);

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
