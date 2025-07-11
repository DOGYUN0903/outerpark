package com.lockers.outerpark.domain.seat.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
		if (!seatRepository.existsByIdAndIsDeletedFalse(seatId)) {
			throw new SeatException.SeatNotFoundException();
		}

		return !reservationSeatRepository.existsActiveBySeatIdAndConcertId(seatId, concertId);
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
		List<Seat> allSeats = seatRepository.findAllActiveSeatsOrderBySeatNumber();

		// DTO Projection을 활용한 성능 최적화
		List<SeatStatusDto> reservedSeatsStatus = reservationSeatRepository.findSeatStatusByConcertId(concertId);
		Map<Long, String> seatStatusMap = reservedSeatsStatus.stream()
			.collect(Collectors.toMap(
				SeatStatusDto::getSeatId,
				SeatStatusDto::getDisplayStatus,
				(existing, replacement) -> existing
			));

		// 전체 좌석에 예약 상태 매핑
		List<SeatResponse> seatResponses = allSeats.stream()
			.map(seat -> {
				String status = seatStatusMap.get(seat.getId());
				return SeatResponse.fromEntityWithStatus(seat, status);
			})
			.toList();

		// 통계 계산
		int totalSeats = seatResponses.size();
		int reservedSeats = (int)seatResponses.stream()
			.filter(seat -> "PENDING".equals(seat.getStatus()) || "CONFIRMED".equals(seat.getStatus()))
			.count();
		int availableSeats = totalSeats - reservedSeats;

		log.info("콘서트 {}의 좌석 현황: 총 {}석, 예약 가능 {}석, 예약됨 {}석",
			concertId, totalSeats, availableSeats, reservedSeats);

		return SeatsStatusResponse.of(concertId, totalSeats, availableSeats, reservedSeats, seatResponses);
	}

	@Override
	@Transactional(readOnly = true)
	public Seat getSeatForReservation(Long seatId, Long concertId) {
		Seat seat = seatRepository.findByIdAndIsDeletedFalse(seatId)
			.orElseThrow(SeatException.SeatNotFoundException::new);

		// 좌석 예약 가능성 검증
		if (!isAvailable(seatId, concertId)) {
			log.warn("좌석 {}은 콘서트 {}에서 이미 예약됨", seatId, concertId);
			throw new SeatException.SeatNotFoundException();
		}

		log.debug("예약용 좌석 조회 완료: seatId={}, concertId={}", seatId, concertId);
		return seat;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seat> getSeatsForReservation(List<Long> seatIds) {
		log.debug("예약용 좌석 일괄 조회 시작 - seatIds: {}", seatIds);

		// 좌석 선택 기본 규칙 검증
		if (seatIds == null || seatIds.isEmpty()) {
			throw new SeatException.InvalidSeatSelectionException("최소 하나의 좌석을 선택해야 합니다.");
		}

		if (seatIds.size() > 2) {
			throw new SeatException.InvalidSeatSelectionException("한 번에 최대 2개의 좌석까지만 예약 가능합니다.");
		}

		// 중복 좌석 선택 검증
		Set<Long> uniqueSeatIds = Set.copyOf(seatIds);
		if (uniqueSeatIds.size() != seatIds.size()) {
			throw new SeatException.InvalidSeatSelectionException("중복된 좌석이 선택되었습니다.");
		}

		// 좌석 존재 여부 일괄 확인
		List<Seat> existingSeats = seatRepository.findAllByIdsAndIsDeletedFalse(seatIds);

		if (existingSeats.size() != seatIds.size()) {
			Set<Long> foundSeatIds = existingSeats.stream()
				.map(Seat::getId)
				.collect(Collectors.toSet());

			List<Long> missingSeatIds = seatIds.stream()
				.filter(id -> !foundSeatIds.contains(id))
				.toList();

			throw new SeatException.SeatNotFoundException(
				String.format("존재하지 않는 좌석: %s", missingSeatIds));
		}

		// 좌석 번호 순으로 정렬하여 반환
		List<Seat> sortedSeats = existingSeats.stream()
			.sorted((s1, s2) -> s1.getSeatNumber().compareTo(s2.getSeatNumber()))
			.toList();

		log.debug("좌석 조회 완료 - {} 개의 유효한 좌석 확인", sortedSeats.size());
		return sortedSeats;
	}
}
