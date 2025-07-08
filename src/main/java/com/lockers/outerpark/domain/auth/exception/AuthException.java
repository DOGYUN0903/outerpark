package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class AuthException {
	public static class InvalidPasswordException extends BusinessException {
		public InvalidPasswordException() {
			super(AuthErrorCode.INVALID_PASSWORD);
		}
	}

	public static class InvalidTokenException extends BusinessException {
		public InvalidTokenException() {
			super(AuthErrorCode.TOKEN_NOT_FOUND);
		}
	}
}
