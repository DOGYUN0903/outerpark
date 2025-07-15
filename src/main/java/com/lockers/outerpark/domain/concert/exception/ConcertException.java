package com.lockers.outerpark.domain.concert.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.common.exception.ErrorCode;

public class ConcertException extends BusinessException {
	public ConcertException(ErrorCode errorCode) {
		super(errorCode);
	}
}
