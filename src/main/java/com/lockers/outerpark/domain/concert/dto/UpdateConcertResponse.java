package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;
import com.lockers.outerpark.domain.concert.entity.Concert;

public record UpdateConcertResponse(String title, UserDto writer, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static UpdateConcertResponse of(Concert concert) {
        return new UpdateConcertResponse(
            concert.getTitle(),
            new UserDto(concert.getWriter()),
            concert.getCreatedAt(),
            concert.getUpdatedAt()
        );
    }
}
