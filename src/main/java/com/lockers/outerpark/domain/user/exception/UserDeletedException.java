package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class UserDeletedException extends BusinessException {
    public UserDeletedException() {
        super(UserErrorCode.USER_ALREADY_DELETED);
    }
}
