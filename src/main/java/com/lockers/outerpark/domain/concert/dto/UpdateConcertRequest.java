package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class UpdateConcertRequest {

    private String title;

    private Integer runningTime;

    private Integer price;

    private Integer limitAge;

    private LocalDate performanceDate;
}
