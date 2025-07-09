package com.lockers.outerpark.domain.seat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.exception.SeatException;
import com.lockers.outerpark.domain.seat.repository.SeatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

	private final SeatRepository seatRepository;

	@Override
	public List<Seat> getAllSeats() {
		return seatRepository.findByIsDeletedFalse();
	}

	@Override
	public Seat getSeat(Long seatId) {
		return seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);
	}
}
