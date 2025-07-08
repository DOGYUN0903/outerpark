package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;

import lombok.Getter;

@Getter
public class UpdateConcertResponse {
    private final String title;
    private final UserDto user;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateConcertResponse(String title, UserDto user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
