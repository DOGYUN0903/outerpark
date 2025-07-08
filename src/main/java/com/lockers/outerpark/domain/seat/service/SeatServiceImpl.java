package com.lockers.outerpark.domain.seat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.seat.dto.response.SeatResponse;
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

	// =========== Reservation 필요 메서드 =============

	@Override
	@Transactional
	public void reserveSeat(Long seatId) {
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		seat.reserve();

		log.info("좌석 예약 완료: seatId={}, seatNumber={}", seatId, seat.getSeatNumber());
	}

	// =========== Reservation & Payment 필요 메서드 =============

	@Override
	@Transactional
	public void cancelSeatReservation(Long seatId) {
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		seat.cancelSeatReservation();
	}

	@Override
	@Transactional
	public SeatResponse getSeat(Long seatId) {
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		return SeatResponse.fromEntity(seat);
	}

	// =========== Concert 필요 메서드 =============

	@Override
	@Transactional
	public void createSeatsForConcert(Concert concert, int seatCount) {
		long existingSeatCount = seatRepository.countByConcertIdAndIsDeletedFalse(concert.getId());
		if (existingSeatCount > 0) {
			throw new SeatException.DuplicateSeatsExistException();
		}

		// 좌석 생성
		List<Seat> seats = new ArrayList<>();
		for (int seatNumber = 1; seatNumber <= seatCount; seatNumber++) {
			seats.add(new Seat(concert, seatNumber));
		}

		seatRepository.saveAll(seats);

		log.info("콘서트 ID {}에 {}개의 좌석을 생성했습니다.", concert.getId(), seatCount);
	}

	@Override
	@Transactional
	public void deleteAllSeatsByConcert(Long concertId) {

		seatRepository.softDeleteAllByConcertId(concertId);

		log.info("콘서트 ID {}의 모든 좌석을 삭제했습니다.", concertId);

	}
}
