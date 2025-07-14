package com.lockers.outerpark.common.exception;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lockers.outerpark.common.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 1. 비즈니스 로직에서 발생한 커스텀 예외 처리
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
		return ResponseEntity
			.status(e.getErrorCode().getStatus()) // 예외에 정의된 상태 코드로 응답
			.body(ApiResponse.error(e.getMessage())); // 공통 응답 포맷으로 메시지 반환
	}

	// 2. @Valid로 DTO를 검증할 때 발생하는 예외 처리 (RequestBody 검증 실패)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse<List<String>>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {

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

	// 3. null 값을 입력하였을 경우 (예: {"username" : ,
	//                             "password" : "SnakeJJangJJangMan!"}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Object>> handleJsonParseError(HttpMessageNotReadableException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.error("입력 값을 다시 확인해주세요."));
	}

	// 4. 예상하지 못한 런타임 예외 처리
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("예상치 못한 오류가 발생했습니다."));
	}

	// 5. 그 외 모든 예외 처리 (최후의 보루)
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("서버 내부 오류가 발생했습니다."));
	}
}
