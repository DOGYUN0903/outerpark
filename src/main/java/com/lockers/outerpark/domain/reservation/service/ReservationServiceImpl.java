package com.lockers.outerpark.domain.reservation.service;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        return null;
    }

    @Override
    public void cancelReservation(Long reservationId) {

    }
}
