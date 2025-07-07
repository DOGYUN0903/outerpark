package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다.", 401),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", 400);

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
