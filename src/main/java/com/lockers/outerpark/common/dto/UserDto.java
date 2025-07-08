package com.lockers.outerpark.common.dto;

import com.lockers.outerpark.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserDto {
    private final Long userId;
    private final String nickname;

    public UserDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
    }
}
