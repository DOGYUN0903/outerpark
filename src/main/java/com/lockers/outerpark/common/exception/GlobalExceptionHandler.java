package com.lockers.outerpark.common.exception;

import com.lockers.outerpark.common.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 비즈니스 로직에서 발생한 커스텀 예외 처리
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus()) // 예외에 정의된 상태 코드로 응답
                .body(ApiResponse.error(e.getMessage())); // 공통 응답 포맷으로 메시지 반환
    }

    // 3. @Valid로 DTO를 검증할 때 발생하는 예외 처리 (RequestBody 검증 실패)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // 검증 실패한 메시지를 모두 모아서 리스트로 반환
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.error(String.join("||", errors))); // || 구분자로 묶어 반환
    }

    // null 값을 입력하였을 경우 (예: {"username" : ,
    //                             "password" : "SnakeJJangJJangMan!"}
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonParseError(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("입력값이 누락되었습니다. 확인해주세요."));
    }
}
