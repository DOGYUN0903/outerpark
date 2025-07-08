package com.lockers.outerpark.domain.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.request.WithdrawRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ApiResponse.success("회원가입에 성공하였습니다.", authService.signup(signupRequest));
    }

    // 로그인
    @PostMapping("/signin")
    public ApiResponse<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
        return ApiResponse.success("로그인에 성공하였습니다.", authService.signin(signinRequest));
    }

    // 회원 탈퇴
    @DeleteMapping("/withdraw")
    public ApiResponse<Void> withdraw(@AuthenticationPrincipal Long userId,
        @RequestBody WithdrawRequest withdrawRequest) {
        authService.withdraw(userId, withdrawRequest);
        return ApiResponse.success("회원 탈퇴에 성공하였습니다.", null);
    }
}
