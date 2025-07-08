package com.lockers.outerpark.domain.seat.service;

import com.lockers.outerpark.domain.seat.dto.response.SeatResponse;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

    @Override
    public boolean isReservable(Long seatId) {
        return false;
    }

    @Override
    public void reserveSeat(Long seatId) {

    }

    @Override
    public void cancelSeatReservation(Long seatId) {

    }

    @Override
    public SeatResponse getSeat(Long seatId) {
        return null;
    }

    @Override
    public void createSeatsForConcert(Long concertId) {

    }

    @Override
    public void createSeatsForConcert(Long concertId, int seatCount) {

    }

    @Override
    public void deleteAllSeatsByConcert(Long concertId) {

    }
}
