package com.lockers.outerpark.domain.seat.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SeatErrorCode implements ErrorCode {
	SEAT_NOT_FOUND("존재하지 않는 좌석입니다.", HttpStatus.NOT_FOUND),
	SEAT_ALREADY_RESERVED("이미 예약된 좌석입니다.", HttpStatus.CONFLICT),
	SEAT_NOT_RESERVED("예약되지 않은 좌석입니다.", HttpStatus.BAD_REQUEST),
	SEAT_ALREADY_DELETED("이미 삭제된 좌석입니다.", HttpStatus.GONE);

	private final String message;
	private final HttpStatus status;

	@Override
	public String getMessage() {
		return "";
	}

	@Override
	public HttpStatus getStatus() {
		return null;
	}
}
