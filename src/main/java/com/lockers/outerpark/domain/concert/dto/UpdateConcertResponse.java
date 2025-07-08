package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;
import com.lockers.outerpark.domain.concert.entity.Concert;

import lombok.Getter;

@Getter
public class UpdateConcertResponse {
    private final String title;
    private final UserDto writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateConcertResponse(Concert concert) {
        this.title = concert.getTitle();
        this.writer = new UserDto(concert.getWriter());
        this.createdAt = concert.getCreatedAt();
        this.updatedAt = concert.getUpdatedAt();
    }
}
