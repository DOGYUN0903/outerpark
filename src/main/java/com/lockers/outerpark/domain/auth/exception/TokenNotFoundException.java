package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.BusinessException;
import com.lockers.outerpark.common.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException() {
        super(AuthErrorCode.TOKEN_NOT_FOUND);
    }
}
