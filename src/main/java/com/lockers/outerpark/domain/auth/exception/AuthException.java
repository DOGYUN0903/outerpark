package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class AuthException {
    public static class InvalidPasswordException extends BusinessException {
        public InvalidPasswordException() {
            super(AuthErrorCode.INVALID_PASSWORD);
        }
    }
}
