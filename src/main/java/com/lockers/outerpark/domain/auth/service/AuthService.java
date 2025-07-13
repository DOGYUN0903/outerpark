package com.lockers.outerpark.domain.auth.service;

import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.request.WithdrawRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;

public interface AuthService {

	SignupResponse signup(SignupRequest signupRequest);

	SigninResponse signin(SigninRequest signinRequest);

	void withdraw(Long userId, WithdrawRequest withdrawRequest);
}
