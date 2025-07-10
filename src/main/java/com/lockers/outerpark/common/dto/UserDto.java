package com.lockers.outerpark.common.dto;

import com.lockers.outerpark.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserDto {
    private final Long userId;
    private final String nickname;

    private UserDto(Long id, String nickname) {
        this.userId = id;
        this.nickname = nickname;
    }

    public static UserDto of(User user) {
        return new UserDto(
            user.getId(),
            user.getNickname()
        );
    }
}
