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
			return null; // 예약 가능
		}

		ReservationStatus reservationStatus = activeReservation.get().getReservation().getStatus();

		switch (reservationStatus) {
			case CONFIRMED:
				return SeatStatus.CONFIRMED.name();
			case CANCELLED:
				return null; // 취소된 예약은 예약 가능으로 처리
			case PENDING:
				return SeatStatus.PENDING.name();
			default:
				return null;
		}
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
	public List<Seat> getSeatsForReservation(List<Long> seatIds, Long concertId) {
		log.debug("예약용 좌석 일괄 조회 및 검증 시작 - seatIds: {}, concertId: {}", seatIds, concertId);

		// 1. 좌석 예약 가능 여부 일괄 검증
		validateSeatsAvailability(seatIds, concertId);

		// 2. 좌석 객체 조회 (이미 검증되었으므로 안전)
		List<Seat> seats = seatRepository.findAllByIdsAndIsDeletedFalse(seatIds);

		// 3. 좌석 번호 순으로 정렬하여 반환
		List<Seat> sortedSeats = seats.stream()
			.sorted((s1, s2) -> s1.getSeatNumber().compareTo(s2.getSeatNumber()))
			.toList();

		log.debug("좌석 조회 및 검증 완료 - {} 개의 유효한 좌석 확인", sortedSeats.size());
		return sortedSeats;
	}

	@Override
	@Transactional(readOnly = true)
	public void validateSeatsAvailability(List<Long> seatIds, Long concertId) {
		// 기본 입력값 검증
		if (seatIds == null || seatIds.isEmpty()) {
			throw new SeatException.InvalidSeatSelectionException("좌석을 선택해주세요.");
		}

		// 중복 좌석 검증
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

		List<Long> unavailableSeats = getUnavailableSeats(seatIds, concertId);

		if (!unavailableSeats.isEmpty()) {
			log.warn("예약 불가능한 좌석 발견 - concertId: {}, unavailableSeats: {}",
				concertId, unavailableSeats);
			throw new SeatException.SeatAlreadyReservedException();
		}

		log.debug("모든 좌석 예약 가능 확인 완료 - concertId: {}, validSeats: {}", concertId, seatIds);
	}

	/**
	 * 예약 불가능한 좌석 ID 목록을 반환 (성능 최적화된 단일 쿼리)
	 */
	private List<Long> getUnavailableSeats(List<Long> seatIds, Long concertId) {
		// ReservationSeatRepository에 새로운 메서드 추가 필요
		return reservationSeatRepository.findUnavailableSeatIds(seatIds, concertId);
	}
}
