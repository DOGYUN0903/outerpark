package com.lockers.outerpark.domain.reservation.dto.response;

import java.time.LocalDate;

public record ReservationConcertInfo(Long id, String title, LocalDate performanceDate) {

}
