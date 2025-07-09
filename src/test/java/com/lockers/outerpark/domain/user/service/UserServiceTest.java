package com.lockers.outerpark.domain.user.service;

import static com.lockers.outerpark.domain.user.exception.UserException.*;
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

import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.entity.UserRole;
import com.lockers.outerpark.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    // getActiveUserById 단위 테스트 시작
    @Test
    @DisplayName("회원이 없으면 예외 발생")
    void 회원이_없으면_예외_발생() {
        // given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getActiveUserById(userId))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("탈퇴한 회원이면 예외 발생")
    void 탈퇴한_회원이면_예외_발생() {
        // given
        Long userId = 1L;
        User deletedUser = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
            UserRole.USER);
        deletedUser.softDelete();

        given(userRepository.findById(userId)).willReturn(Optional.of(deletedUser));

        // when & then
        assertThatThrownBy(() -> userService.getActiveUserById(userId))
            .isInstanceOf(UserDeletedException.class);
    }

    @Test
    @DisplayName("회원 조회 성공")
    void 회원_조회_성공() {
        // given
        Long userId = 1L;
        User user = new User("test@email.com", "nickname", LocalDate.parse("2000-01-01"), "1234", 100000L,
            UserRole.USER);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        User findUser = userService.getActiveUserById(userId);

        // then
        assertThat(findUser).isEqualTo(user);
    }
    // getActiveUserById 단위 테스트 끝
}