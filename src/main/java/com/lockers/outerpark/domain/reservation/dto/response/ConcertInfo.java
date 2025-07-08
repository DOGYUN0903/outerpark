package com.lockers.outerpark.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertInfo {

    private String concertTitle;

    private LocalDateTime concertDate;
}
