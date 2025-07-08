package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lockers.outerpark.common.dto.UserDto;
import com.lockers.outerpark.domain.concert.entity.Concert;

import lombok.Getter;

@Getter
public class FindConcertResponse {
    private final String title;
    private final Integer runningTime;
    private final UserDto writer;
    private final Integer price;
    private final Integer limitAge;
    private final LocalDate performanceDate;
    private final LocalDateTime updatedAt;

    public FindConcertResponse(Concert concert) {
        this.title = concert.getTitle();
        this.runningTime = concert.getRunningTime();
        this.writer = new UserDto(concert.getWriter());
        this.price = concert.getPrice();
        this.limitAge = concert.getLimitAge();
        this.performanceDate = concert.getPerformanceDate();
        this.updatedAt = concert.getUpdatedAt();
    }
}
