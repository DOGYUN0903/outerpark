package com.lockers.outerpark.domain.seat.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.common.exception.ErrorCode;

public class SeatException extends BusinessException {

	public SeatException(ErrorCode errorCode) {
		super(errorCode);
	}
}