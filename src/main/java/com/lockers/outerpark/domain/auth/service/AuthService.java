package com.lockers.outerpark.domain.auth.service;

import com.lockers.outerpark.common.jwt.JwtUtil;
import com.lockers.outerpark.domain.auth.dto.request.SigninRequest;
import com.lockers.outerpark.domain.auth.dto.request.SignupRequest;
import com.lockers.outerpark.domain.auth.dto.response.SigninResponse;
import com.lockers.outerpark.domain.auth.dto.response.SignupResponse;
import com.lockers.outerpark.domain.auth.exception.InvalidPasswordException;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.exception.EmailAlreadyExistsException;
import com.lockers.outerpark.domain.user.exception.UserNotFoundException;
import com.lockers.outerpark.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        // 이메일 중복 체크
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User user = new User(
                signupRequest.getEmail(),
                signupRequest.getNickname(),
                signupRequest.getBirth(),
                encodedPassword,
                100000L,
                userRole
        );

        User savedUser = userRepository.save(user);

        String bearerToken = jwtUtil.createToken(savedUser.getId());

        return new SignupResponse(bearerToken);
    }

    public SigninResponse signin(SigninRequest signinRequest) {

        User findUser = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401 반환
        if (!passwordEncoder.matches(signinRequest.getPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        String bearerToken = jwtUtil.createToken(findUser.getId());

        return new SigninResponse(bearerToken);
    }
}
