package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.common.exception.ErrorCode;

public class UserException extends BusinessException {

	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
