package com.lockers.outerpark.domain.auth.service;

import static com.lockers.outerpark.domain.user.exception.UserException.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lockers.outerpark.common.jwt.JwtUtil;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
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

    @Test
    @DisplayName("이메일 중복이면 예외가 발생한다")
    void 이메일_중복이면_예외가_발생한다() {
        // given
        SignupRequest signupRequest = new SignupRequest("test@email.com", "test", LocalDate.parse("2000-01-01"),
            "Test1234@", "USER");
        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(true);
        // when & then
        assertThatThrownBy(() -> authService.signup(signupRequest))
            .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @Test
    @DisplayName("닉네임 중복이면 예외가 발생한다")
    void 닉네임_중복이면_예외가_발생한다() {
        // given
        SignupRequest signupRequest = new SignupRequest("test1@email.com", "test1", LocalDate.parse("2000-09-03"),
            "Test1234@", "USER");
        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(false);
        given(userRepository.existsByNickname(signupRequest.getNickname())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(signupRequest))
            .isInstanceOf(NicknameAlreadyExistsException.class);
    }

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입_성공() {
        // given
        SignupRequest signupRequest = new SignupRequest("test1@email.com", "test1", LocalDate.parse("2000-09-03"),
            "Test1234@", "USER");
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
}