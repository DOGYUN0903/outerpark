package com.lockers.outerpark.domain.seat.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SeatErrorCode implements ErrorCode {
	SEAT_NOT_FOUND("존재하지 않는 좌석입니다.", HttpStatus.NOT_FOUND),
	SEAT_ALREADY_RESERVED("이미 예약된 좌석입니다.", HttpStatus.CONFLICT),
	SEAT_NOT_RESERVED("예약되지 않은 좌석입니다.", HttpStatus.BAD_REQUEST),
	SEAT_ALREADY_DELETED("이미 삭제된 좌석입니다.", HttpStatus.GONE),
	DUPLICATE_SEATS_EXIST("이미 좌석이 생성된 콘서트입니다.", HttpStatus.CONFLICT),
	SEAT_LOCK_CONFLICT("이미 예약 처리 중인 좌석이 있습니다.", HttpStatus.CONFLICT),
	INVALID_SEAT_SELECTION("잘못된 좌석 선택입니다.", HttpStatus.BAD_REQUEST);

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
