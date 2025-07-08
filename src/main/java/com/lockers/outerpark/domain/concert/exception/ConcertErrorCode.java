package com.lockers.outerpark.domain.concert.exception;

import org.springframework.http.HttpStatus;

import com.lockers.outerpark.common.exception.ErrorCode;

public enum ConcertErrorCode implements ErrorCode {
    CONCERT_NOT_FOUND("공연이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ConcertErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

}
