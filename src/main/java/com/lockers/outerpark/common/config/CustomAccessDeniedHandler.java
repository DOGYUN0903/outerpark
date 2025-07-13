package com.lockers.outerpark.common.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.lockers.outerpark.domain.auth.exception.AuthErrorCode;
import com.lockers.outerpark.domain.auth.exception.AuthException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) {
		log.error("[Access is Denied] 접근 불가");
		throw new AuthException(AuthErrorCode.TOKEN_NOT_FOUND);
	}

}
