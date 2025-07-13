package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class AuthException extends BusinessException {

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}
}
