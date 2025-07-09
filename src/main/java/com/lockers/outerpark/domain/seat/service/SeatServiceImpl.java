package com.lockers.outerpark.domain.seat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.seat.dto.response.SeatsStatusResponse;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.exception.SeatException;
import com.lockers.outerpark.domain.seat.repository.ReservationSeatRepository;
import com.lockers.outerpark.domain.seat.repository.SeatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

	private final SeatRepository seatRepository;
	private final ReservationSeatRepository reservationSeatRepository;

	@Override
	@Transactional
	public boolean isAvailable(Long seatId) {
		// 좌석 존재확인
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		// 예약 정보 확인
		return false;
	}

	@Override
	public String getSeatStatus(Long seatId) {
		return "";
	}

	@Override
	public SeatsStatusResponse getSeatsForConcert(Long concertId) {
		return null;
	}
}
