package com.lockers.outerpark.domain.auth.service;

import static com.lockers.outerpark.domain.auth.exception.AuthException.*;
import static com.lockers.outerpark.domain.user.exception.UserException.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.common.jwt.JwtUtil;
import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.request.WithdrawRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.repository.UserRepository;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Transactional
	public SignupResponse signup(SignupRequest signupRequest) {

		validateDuplicateUserInfo(signupRequest.getEmail(), signupRequest.getNickname());

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

		User user = signupRequest.toEntity(encodedPassword);

		User savedUser = userRepository.save(user);

		String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getUserRole());

		return new SignupResponse(bearerToken);
	}

	@Transactional(readOnly = true)
	public SigninResponse signin(SigninRequest signinRequest) {

		User findUser = userRepository.findByEmail(signinRequest.getEmail())
			.orElseThrow(UserNotFoundException::new);

		if (findUser.getIsDeleted()) {
			throw new UserDeletedException();
		}

		// 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401 반환
		if (!passwordEncoder.matches(signinRequest.getPassword(), findUser.getPassword())) {
			throw new InvalidPasswordException();
		}

		String bearerToken = jwtUtil.createToken(findUser.getId(), findUser.getUserRole());

		return new SigninResponse(bearerToken);
	}

	@Transactional
	public void withdraw(Long userId, WithdrawRequest withdrawRequest) {
		// 유저 찾기 + 탈퇴 여부 확인
		User user = userService.getActiveUserById(userId);

		if (!passwordEncoder.matches(withdrawRequest.getPassword(), user.getPassword())) {
			throw new InvalidPasswordException();
		}
		user.softDelete();
	}

	// 이메일 + 닉네임 중복체크
	private void validateDuplicateUserInfo(String email, String nickname) {
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyExistsException();
		}

		if (userRepository.existsByNickname(nickname)) {
			throw new NicknameAlreadyExistsException();
		}
	}
}
