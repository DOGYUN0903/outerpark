package com.lockers.outerpark.domain.seat.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.common.exception.ErrorCode;

public class SeatException extends BusinessException {
	public SeatException(ErrorCode errorCode) {
		super(errorCode);
	}

	public static SeatException notFound() {
		return new SeatException(SeatErrorCode.SEAT_NOT_FOUND);
	}

	public static SeatException alreadyReserved() {
		return new SeatException(SeatErrorCode.SEAT_ALREADY_RESERVED);
	}

	public static SeatException notReserved() {
		return new SeatException(SeatErrorCode.SEAT_NOT_RESERVED);
	}

	public static SeatException alreadyDeleted() {
		return new SeatException(SeatErrorCode.SEAT_ALREADY_DELETED);
	}
}
