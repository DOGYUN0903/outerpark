package com.lockers.outerpark.domain.reservation.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class ReservationException extends BusinessException {

	public ReservationException(ReservationErrorCode errorCode) {
		super(errorCode);
	}
}
