package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class InvalidUserRoleException extends BusinessException {
    public InvalidUserRoleException() {
        super(UserErrorCode.INVALID_USER_ROLE);
    }
}
