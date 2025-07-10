package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;
import com.lockers.outerpark.domain.concert.entity.Concert;

public record FindConcertResponse(String title, Integer runningTime, UserDto writer, Integer price, Integer limitAge,
                                  LocalDate performanceDate, LocalDateTime updatedAt) {

    public static FindConcertResponse of(Concert concert) {
        return new FindConcertResponse(
            concert.getTitle(),
            concert.getRunningTime(),
            new UserDto(concert.getWriter()),
            concert.getPrice(),
            concert.getLimitAge(),
            concert.getPerformanceDate(),
            concert.getUpdatedAt()
        );
    }
}
