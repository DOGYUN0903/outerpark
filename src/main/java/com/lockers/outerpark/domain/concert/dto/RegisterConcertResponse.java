package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;
import com.lockers.outerpark.domain.concert.entity.Concert;

public record RegisterConcertResponse(String title, UserDto user, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static RegisterConcertResponse of(Concert concert) {
        return new RegisterConcertResponse(
            concert.getTitle(),
            new UserDto(concert.getWriter()),
            concert.getCreatedAt(),
            concert.getUpdatedAt()
        );
    }
}
