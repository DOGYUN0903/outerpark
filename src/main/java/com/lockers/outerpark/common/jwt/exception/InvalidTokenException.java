package com.lockers.outerpark.common.jwt.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.domain.auth.exception.AuthErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super(AuthErrorCode.TOKEN_NOT_FOUND);
    }
}
