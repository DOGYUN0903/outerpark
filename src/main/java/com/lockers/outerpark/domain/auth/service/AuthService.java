package com.lockers.outerpark.domain.auth.service;

import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.request.WithdrawRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.user.exception.UserException;

public interface AuthService {

	/**
	 * 회원가입 요청 정보를 바탕으로 새로운 사용자를 등록합니다.
	 *
	 * @param signupRequest 회원가입 요청 정보
	 * @return 회원가입 결과를 담은 {@link SignupResponse}
	 * @throws UserException 중복된 사용자 정보가 존재하거나 유효하지 않은 경우
	 */
	SignupResponse signup(SignupRequest signupRequest);

	/**
	 * 로그인 요청 정보를 기반으로 인증을 수행하고 토큰을 발급합니다.
	 *
	 * @param signinRequest 로그인 요청 정보
	 * @return 로그인 결과를 담은 {@link SigninResponse}
	 * @throws UserException 이메일 또는 비밀번호가 일치하지 않는 경우
	 */
	SigninResponse signin(SigninRequest signinRequest);

	/**
	 * 현재 로그인한 사용자의 회원 탈퇴를 수행합니다.
	 *
	 * @param userId 탈퇴를 요청한 사용자 ID
	 * @param withdrawRequest 탈퇴 요청 관련 정보 (비밀번호 등)
	 * @throws UserException 비밀번호가 일치하지 않거나 탈퇴 불가능한 상태인 경우
	 */
	void withdraw(Long userId, WithdrawRequest withdrawRequest);
}
