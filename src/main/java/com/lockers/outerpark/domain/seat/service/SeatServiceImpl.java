package com.lockers.outerpark.domain.seat.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import com.lockers.outerpark.domain.seat.dto.response.SeatResponse;
import com.lockers.outerpark.domain.seat.dto.response.SeatStatusDto;
import com.lockers.outerpark.domain.seat.dto.response.SeatsStatusResponse;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.entity.SeatStatus;
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
	@Transactional(readOnly = true)
	public boolean isAvailable(Long seatId, Long concertId) {
		seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		Optional<ReservationSeat> activeReservation =
			reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId);

		if (activeReservation.isEmpty()) {
			return true;
		}

		ReservationStatus status = activeReservation.get().getReservation().getStatus();
		return status == ReservationStatus.CANCELLED;
	}

	@Override
	@Transactional(readOnly = true)
	public String getSeatStatus(Long seatId, Long concertId) {
		Optional<ReservationSeat> activeReservation =
			reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId);

		if (activeReservation.isEmpty()) {
			return null;
		}

		ReservationStatus reservationStatus = activeReservation.get().getReservation().getStatus();

		if (reservationStatus == ReservationStatus.CANCELLED) {
			return null;
		}

		// TODO: PENDING 아직 없음 * 추가 예정
		if (reservationStatus == ReservationStatus.PENDING) {
			return SeatStatus.PENDING.name();
		}

		if (reservationStatus == ReservationStatus.CONFIRMED) {
			return SeatStatus.CONFIRMED.name();
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public SeatsStatusResponse getSeatsForConcert(Long concertId) {
		List<Seat> seats = seatRepository.findByConcertIdAndIsDeletedFalseOrderBySeatNumber(concertId);

		// DTO Projection을 활용한 성능 최적화
		List<SeatStatusDto> seatStatusList = reservationSeatRepository.findSeatStatusByConcertId(concertId);
		Map<Long, String> seatStatusMap = seatStatusList.stream()
			.collect(Collectors.toMap(
				SeatStatusDto::getSeatId,
				SeatStatusDto::getDisplayStatus,
				(existing, replacement) -> existing
			));

		List<SeatResponse> seatResponses = seats.stream()
			.map(seat -> {
				String status = seatStatusMap.get(seat.getId());
				return SeatResponse.fromEntityWithStatus(seat, status);
			})
			.toList();

		log.info("콘서트 {}의 좌석 현황 조회 완료: 총 {}석", concertId, seats.size());

		return SeatsStatusResponse.ofWithStatus(concertId, seatResponses);
	}

	@Override
	@Transactional(readOnly = true)
	public Seat getSeatForReservation(Long seatId, Long concertId) {
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		// 콘서트 ID 검증
		if (!seat.getConcert().getId().equals(concertId)) {
			log.warn("좌석 {}이 콘서트 {}에 속하지 않음", seatId, concertId);
			throw new SeatException.SeatNotFoundException();
		}

		log.debug("예약용 좌석 조회 완료: seatId={}, concertId={}", seatId, concertId);
		return seat;
	}
}
