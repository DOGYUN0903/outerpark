package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    INVALID_USER_ROLE("유효하지 않은 권한입니다.", 400);

    private final String message;
    private final int status;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
