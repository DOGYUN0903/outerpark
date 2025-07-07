package com.lockers.outerpark.common.exception;

import com.lockers.outerpark.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 비즈니스 로직에서 발생한 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {

        return ResponseEntity
                .status(e.getErrorCode().getStatus()) // 예외에 정의된 상태 코드로 응답
                .body(ApiResponse.error(e.getMessage())); // 공통 응답 포맷으로 메시지 반환
    }
}
