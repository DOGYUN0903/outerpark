package com.lockers.outerpark.domain.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lockers.outerpark.common.jwt.JwtUtil;
import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.request.WithdrawRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.exception.UserException;
import com.lockers.outerpark.domain.user.repository.UserRepository;
import com.lockers.outerpark.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtUtil jwtUtil;
	@InjectMocks
	private AuthService authService;

	// 회원가입 단위테스트 시작
	@Test
	@DisplayName("이메일 중복이면 예외가 발생한다")
	void 이메일_중복이면_예외가_발생한다() {
		// given
		SignupRequest signupRequest = new SignupRequest("test@email.com", "test", LocalDate.parse("2000-01-01"),
			"Test1234@");
		given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(true);
		// when & then
		assertThatThrownBy(() -> authService.signup(signupRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("닉네임 중복이면 예외가 발생한다")
	void 닉네임_중복이면_예외가_발생한다() {
		// given
		SignupRequest signupRequest = new SignupRequest("test1@email.com", "test1", LocalDate.parse("2000-09-03"),
			"Test1234@");
		given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(false);
		given(userRepository.existsByNickname(signupRequest.getNickname())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> authService.signup(signupRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("회원가입 성공")
	void 회원가입_성공() {
		// given
		SignupRequest signupRequest = new SignupRequest("test1@email.com", "test1", LocalDate.parse("2000-09-03"),
			"Test1234@");
		given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(false);
		given(userRepository.existsByNickname(signupRequest.getNickname())).willReturn(false);
		given(passwordEncoder.encode(signupRequest.getPassword())).willReturn("Test1234@");
		given(userRepository.save(any(User.class))).willReturn(
			new User("test1@email.com", "test1", LocalDate.parse("2000-09-03"), "Test1234@", 100000L, UserRole.USER)
		);
		given(jwtUtil.createToken(any(), any())).willReturn("token123");

		// when
		SignupResponse response = authService.signup(signupRequest);

		// then
		assertThat(response.getToken()).isEqualTo("token123");
	}
	// 회원가입 단위테스트 끝

	// 로그인 단위테스트 시작
	@Test
	@DisplayName("유저가 없으면 예외발생")
	void 유저가_없으면_예외발생() {
		// given
		SigninRequest signinRequest = new SigninRequest("test@email.com", "1234");
		given(userRepository.findByEmail(signinRequest.getEmail())).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> authService.signin(signinRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("탈퇴한 유저라면 예외발생")
	void 탈퇴한_유저라면_예외발생() {
		// given
		SigninRequest signinRequest = new SigninRequest("test@email.com", "1234");
		User deletedUser = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
			UserRole.USER);
		deletedUser.softDelete();

		given(userRepository.findByEmail(signinRequest.getEmail())).willReturn(Optional.of(deletedUser));

		// when & then
		assertThatThrownBy(() -> authService.signin(signinRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("비밀번호가 틀리면 예외발생")
	void 비밀번호가_틀리면_예외발생() {
		// given
		SigninRequest signinRequest = new SigninRequest("test@email.com", "wrongPassword");
		User user = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "truePassword", 100000L,
			UserRole.USER);

		given(userRepository.findByEmail(signinRequest.getEmail())).willReturn(Optional.of(user));
		given(passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())).willReturn(false);

		assertThatThrownBy(() -> authService.signin(signinRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("로그인 성공")
	void 로그인_성공() {
		// given
		SigninRequest signinRequest = new SigninRequest("test@email.com", "1234");
		User user = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
			UserRole.USER);

		given(userRepository.findByEmail(signinRequest.getEmail())).willReturn(Optional.of(user));
		given(passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())).willReturn(true);
		given(jwtUtil.createToken(any(), any())).willReturn("token1234");

		// when
		SigninResponse signinResponse = authService.signin(signinRequest);

		// then
		assertThat(signinResponse.getToken()).isEqualTo("token1234");
	}
	// 로그인 단위테스트 끝

	// 회원 탈퇴 단위테스트 시작
	@Test
	@DisplayName("회원 탈퇴시 비밀번호가 틀리면 예외발생")
	void 회원_탈퇴시_비밀번호가_틀리면_예외발생() {
		// given
		Long userId = 1L;
		User user = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
			UserRole.USER);
		WithdrawRequest withdrawRequest = new WithdrawRequest("password");

		given(userService.getActiveUserById(userId)).willReturn(user);
		given(passwordEncoder.matches(withdrawRequest.getPassword(), user.getPassword())).willReturn(false);

		assertThatThrownBy(() -> authService.withdraw(userId, withdrawRequest))
			.isInstanceOf(UserException.class);
	}

	@Test
	@DisplayName("회원 탈퇴 성공")
	void 회원_탈퇴_성공() {
		// given
		Long userId = 1L;
		User user = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
			UserRole.USER);
		WithdrawRequest withdrawRequest = new WithdrawRequest("password");

		given(userService.getActiveUserById(userId)).willReturn(user);
		given(passwordEncoder.matches(withdrawRequest.getPassword(), user.getPassword())).willReturn(true);

		// when
		authService.withdraw(userId, withdrawRequest);

		assertThat(user.getIsDeleted()).isTrue();
	}
	// 회원 탈퇴 단위테스트 끝
}