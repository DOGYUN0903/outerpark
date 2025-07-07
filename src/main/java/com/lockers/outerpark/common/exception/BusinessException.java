package com.lockers.outerpark.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());// 부모 클래스에 메시지 전달
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);// 커스텀 메시지 사용
        this.errorCode = errorCode;
    }
}
