package com.lockers.outerpark.domain.reservation.service;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);

    void cancelReservation(Long reservationId);
}
