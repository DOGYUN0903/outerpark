package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;

import lombok.Getter;

@Getter
public class FindConcertResponse {
    private final String title;
    private final Integer runningTime;
    private final UserDto writer;
    private final Integer price;
    private final Integer limitAge;
    private final LocalDate performanceDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FindConcertResponse(String title, Integer runningTime, UserDto writer, Integer price, Integer limitAge,
        LocalDate performanceDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.runningTime = runningTime;
        this.writer = writer;
        this.price = price;
        this.limitAge = limitAge;
        this.performanceDate = performanceDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
