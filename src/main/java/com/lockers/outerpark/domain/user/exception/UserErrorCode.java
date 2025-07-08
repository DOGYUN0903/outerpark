package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    INVALID_USER_ROLE("유효하지 않은 권한입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED("이미 탈퇴한 유저입니다.", HttpStatus.FORBIDDEN);

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
