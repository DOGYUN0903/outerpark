package com.lockers.outerpark.domain.auth.controller;

import com.lockers.outerpark.common.response.ApiResponse;
import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ApiResponse.success("회원가입에 성공하였습니다.", authService.signup(signupRequest));
    }

    @PostMapping("/signin")
    public ApiResponse<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
        return ApiResponse.success("로그인에 성공하였습니다.", authService.signin(signinRequest));
    }
}
