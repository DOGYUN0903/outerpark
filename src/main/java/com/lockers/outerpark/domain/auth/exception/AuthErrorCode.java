package com.lockers.outerpark.domain.auth.exception;

import com.lockers.outerpark.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    TOKEN_NOT_FOUND("토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }


}
