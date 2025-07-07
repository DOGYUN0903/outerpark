package com.lockers.outerpark.common.exception;

public interface ErrorCode {
    String getMessage(); // 에러 메시지

    int getStatus(); // HTTP 상태코드
}
