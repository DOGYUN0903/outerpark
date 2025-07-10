package com.lockers.outerpark.domain.concert.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class RegisterConcertRequest {

    @NotBlank(message = "공연 제목은 필수입니다.")
    private String title;

    @NotNull
    @Positive(message = "공연 시간은 양수여야 합니다.")
    private Integer runningTime;

    @NotNull
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotNull
    @Min(value = 0, message = "관람 가능 연령은 0세 이상이어야 합니다.")
    private Integer limitAge;

    @NotNull(message = "공연 날짜는 필수입니다.")
    @FutureOrPresent(message = "공연 날짜는 오늘 또는 미래여야 합니다.")
    private LocalDate performanceDate;
}
