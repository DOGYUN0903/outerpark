package com.lockers.outerpark.domain.seat.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.type.ReservationStatus;
import com.lockers.outerpark.domain.seat.dto.response.ConcertSeatStatusResponse;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.exception.SeatException;
import com.lockers.outerpark.domain.seat.repository.ReservationSeatRepository;
import com.lockers.outerpark.domain.seat.repository.SeatRepository;
import com.lockers.outerpark.domain.seat.repository.query.SeatStatusInfo;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class SeatServiceImplTest {

	@Mock
	private SeatRepository seatRepository;

	@Mock
	private ReservationSeatRepository reservationSeatRepository;

	@InjectMocks
	private SeatServiceImpl seatService;

	@Test
	@DisplayName("좌석_예약_가능_여부_확인_성공")
	void 좌석_예약_가능_여부_확인_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		given(seatRepository.existsByIdAndIsDeletedFalse(seatId)).willReturn(true);
		given(reservationSeatRepository.existsActiveBySeatIdAndConcertId(seatId, concertId)).willReturn(false);

		// when
		boolean isAvailable = seatService.isAvailable(seatId, concertId);

		// then
		assertThat(isAvailable).isTrue();
	}

	@Test
	@DisplayName("좌석_예약_불가능_여부_확인_성공")
	void 좌석_예약_불가능_여부_확인_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		given(seatRepository.existsByIdAndIsDeletedFalse(seatId)).willReturn(true);
		given(reservationSeatRepository.existsActiveBySeatIdAndConcertId(seatId, concertId)).willReturn(true);

		// when
		boolean isAvailable = seatService.isAvailable(seatId, concertId);

		// then
		assertThat(isAvailable).isFalse();
	}

	@Test
	@DisplayName("존재하지_않는_좌석_예약_가능_여부_확인_실패")
	void 존재하지_않는_좌석_예약_가능_여부_확인_실패() {
		// given
		Long seatId = 999L;
		Long concertId = 1L;

		given(seatRepository.existsByIdAndIsDeletedFalse(seatId)).willReturn(false);

		// when & then
		assertThatThrownBy(() -> seatService.isAvailable(seatId, concertId))
			.isInstanceOf(SeatException.SeatNotFoundException.class);
	}

	@Test
	@DisplayName("좌석_상태_조회_예약_없음_성공")
	void 좌석_상태_조회_예약_없음_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		given(reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId))
			.willReturn(Optional.empty());

		// when
		String status = seatService.getSeatStatus(seatId, concertId);

		// then
		assertThat(status).isNull();
	}

	@Test
	@DisplayName("좌석_상태_조회_PENDING_예약_성공")
	void 좌석_상태_조회_PENDING_예약_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		Reservation reservation = mock(Reservation.class);
		ReservationSeat reservationSeat = mock(ReservationSeat.class);

		given(reservationSeat.getReservation()).willReturn(reservation);
		given(reservation.getStatus()).willReturn(ReservationStatus.PENDING);
		given(reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId))
			.willReturn(Optional.of(reservationSeat));

		// when
		String status = seatService.getSeatStatus(seatId, concertId);

		// then
		assertThat(status).isEqualTo("PENDING");
	}

	@Test
	@DisplayName("좌석_상태_조회_CONFIRMED_예약_성공")
	void 좌석_상태_조회_CONFIRMED_예약_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		Reservation reservation = mock(Reservation.class);
		ReservationSeat reservationSeat = mock(ReservationSeat.class);

		given(reservationSeat.getReservation()).willReturn(reservation);
		given(reservation.getStatus()).willReturn(ReservationStatus.CONFIRMED);
		given(reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId))
			.willReturn(Optional.of(reservationSeat));

		// when
		String status = seatService.getSeatStatus(seatId, concertId);

		// then
		assertThat(status).isEqualTo("CONFIRMED");
	}

	@Test
	@DisplayName("좌석_상태_조회_CANCELLED_예약_성공")
	void 좌석_상태_조회_CANCELLED_예약_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;

		Reservation reservation = mock(Reservation.class);
		ReservationSeat reservationSeat = mock(ReservationSeat.class);

		given(reservationSeat.getReservation()).willReturn(reservation);
		given(reservation.getStatus()).willReturn(ReservationStatus.CANCELLED);
		given(reservationSeatRepository.findActiveBySeatIdAndConcertId(seatId, concertId))
			.willReturn(Optional.of(reservationSeat));

		// when
		String status = seatService.getSeatStatus(seatId, concertId);

		// then
		assertThat(status).isNull();
	}

	@Test
	@DisplayName("콘서트_좌석_목록_조회_성공")
	void 콘서트_좌석_목록_조회_성공() {
		// given
		Long concertId = 1L;

		Seat seat1 = createSeat(1L, "A-1");
		Seat seat2 = createSeat(2L, "A-2");
		List<Seat> seats = List.of(seat1, seat2);

		SeatStatusInfo statusDto1 = new SeatStatusInfo(1L, "A-1", ReservationStatus.PENDING);
		List<SeatStatusInfo> statusDtos = List.of(statusDto1);

		given(seatRepository.findAllActiveSeatsOrderBySeatNumber()).willReturn(seats);
		given(reservationSeatRepository.findSeatStatusByConcertId(concertId)).willReturn(statusDtos);

		// when
		ConcertSeatStatusResponse response = seatService.getSeatsForConcert(concertId);

		// then
		assertThat(response.getConcertId()).isEqualTo(concertId);
		assertThat(response.getTotalSeats()).isEqualTo(2);
		assertThat(response.getAvailableSeats()).isEqualTo(1);
		assertThat(response.getReservedSeats()).isEqualTo(1);
		assertThat(response.getSeats()).hasSize(2);
	}

	@Test
	@DisplayName("예약용_단일_좌석_조회_성공")
	void 예약용_단일_좌석_조회_성공() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;
		Seat seat = createSeat(seatId, "A-1");

		given(seatRepository.findByIdAndIsDeletedFalse(seatId)).willReturn(Optional.of(seat));
		given(seatRepository.existsByIdAndIsDeletedFalse(seatId)).willReturn(true);
		given(reservationSeatRepository.existsActiveBySeatIdAndConcertId(seatId, concertId)).willReturn(false);

		// when
		Seat result = seatService.getSeatForReservation(seatId, concertId);

		// then
		assertThat(result).isEqualTo(seat);
	}

	@Test
	@DisplayName("예약용_단일_좌석_조회_좌석_없음_실패")
	void 예약용_단일_좌석_조회_좌석_없음_실패() {
		// given
		Long seatId = 999L;
		Long concertId = 1L;

		given(seatRepository.findByIdAndIsDeletedFalse(seatId)).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> seatService.getSeatForReservation(seatId, concertId))
			.isInstanceOf(SeatException.SeatNotFoundException.class);
	}

	@Test
	@DisplayName("예약용_단일_좌석_조회_이미_예약됨_실패")
	void 예약용_단일_좌석_조회_이미_예약됨_실패() {
		// given
		Long seatId = 1L;
		Long concertId = 1L;
		Seat seat = createSeat(seatId, "A-1");

		given(seatRepository.findByIdAndIsDeletedFalse(seatId)).willReturn(Optional.of(seat));
		given(seatRepository.existsByIdAndIsDeletedFalse(seatId)).willReturn(true);
		given(reservationSeatRepository.existsActiveBySeatIdAndConcertId(seatId, concertId)).willReturn(true);

		// when & then
		assertThatThrownBy(() -> seatService.getSeatForReservation(seatId, concertId))
			.isInstanceOf(SeatException.SeatNotFoundException.class);
	}

	@Test
	@DisplayName("예약용_다중_좌석_조회_성공")
	void 예약용_다중_좌석_조회_성공() {
		// given
		List<Long> seatIds = List.of(1L, 2L);
		Long concertId = 1L;

		Seat seat1 = createSeat(1L, "A-1");
		Seat seat2 = createSeat(2L, "A-2");
		List<Seat> seats = List.of(seat1, seat2);

		given(seatRepository.findAllByIdsAndIsDeletedFalse(seatIds)).willReturn(seats);
		given(reservationSeatRepository.findUnavailableSeatIds(seatIds, concertId)).willReturn(List.of());

		// when
		List<Seat> result = seatService.getSeatsForReservation(seatIds, concertId);

		// then
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getSeatNumber()).isEqualTo("A-1");
		assertThat(result.get(1).getSeatNumber()).isEqualTo("A-2");
	}

	@Test
	@DisplayName("좌석_유효성_검증_빈_목록_실패")
	void 좌석_유효성_검증_빈_목록_실패() {
		// given
		List<Long> seatIds = List.of();
		Long concertId = 1L;

		// when & then
		assertThatThrownBy(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.isInstanceOf(SeatException.InvalidSeatSelectionException.class)
			.hasMessage("잘못된 좌석 선택입니다.");
	}

	@Test
	@DisplayName("좌석_유효성_검증_null_목록_실패")
	void 좌석_유효성_검증_null_목록_실패() {
		// given
		List<Long> seatIds = null;
		Long concertId = 1L;

		// when & then
		assertThatThrownBy(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.isInstanceOf(SeatException.InvalidSeatSelectionException.class)
			.hasMessage("잘못된 좌석 선택입니다.");
	}

	@Test
	@DisplayName("좌석_유효성_검증_중복_좌석_실패")
	void 좌석_유효성_검증_중복_좌석_실패() {
		// given
		List<Long> seatIds = List.of(1L, 1L, 2L);
		Long concertId = 1L;

		// when & then
		assertThatThrownBy(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.isInstanceOf(SeatException.InvalidSeatSelectionException.class)
			.hasMessage("잘못된 좌석 선택입니다.");
	}

	@Test
	@DisplayName("좌석_유효성_검증_존재하지_않는_좌석_실패")
	void 좌석_유효성_검증_존재하지_않는_좌석_실패() {
		// given
		List<Long> seatIds = List.of(1L, 999L);
		Long concertId = 1L;

		Seat seat1 = createSeat(1L, "A-1");
		given(seatRepository.findAllByIdsAndIsDeletedFalse(seatIds)).willReturn(List.of(seat1));

		// when & then
		assertThatThrownBy(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.isInstanceOf(SeatException.SeatNotFoundException.class)
			.hasMessageContaining("존재하지 않는 좌석");
	}

	@Test
	@DisplayName("좌석_유효성_검증_예약_불가능한_좌석_실패")
	void 좌석_유효성_검증_예약_불가능한_좌석_실패() {
		// given
		List<Long> seatIds = List.of(1L, 2L);
		Long concertId = 1L;

		Seat seat1 = createSeat(1L, "A-1");
		Seat seat2 = createSeat(2L, "A-2");
		List<Seat> seats = List.of(seat1, seat2);

		given(seatRepository.findAllByIdsAndIsDeletedFalse(seatIds)).willReturn(seats);
		given(reservationSeatRepository.findUnavailableSeatIds(seatIds, concertId)).willReturn(List.of(1L));

		// when & then
		assertThatThrownBy(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.isInstanceOf(SeatException.SeatAlreadyReservedException.class);
	}

	@Test
	@DisplayName("좌석_유효성_검증_성공")
	void 좌석_유효성_검증_성공() {
		// given
		List<Long> seatIds = List.of(1L, 2L);
		Long concertId = 1L;

		Seat seat1 = createSeat(1L, "A-1");
		Seat seat2 = createSeat(2L, "A-2");
		List<Seat> seats = List.of(seat1, seat2);

		given(seatRepository.findAllByIdsAndIsDeletedFalse(seatIds)).willReturn(seats);
		given(reservationSeatRepository.findUnavailableSeatIds(seatIds, concertId)).willReturn(List.of());

		// when & then
		assertThatCode(() -> seatService.validateSeatsAvailability(seatIds, concertId))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("좌석_자연순_정렬_테스트")
	void 좌석_자연순_정렬_테스트() {
		// given
		List<Long> seatIds = List.of(1L, 2L, 3L, 4L);
		Long concertId = 1L;

		// A-10, A-1, B-2, A-2 순서로 Mock 데이터 생성
		Seat seat1 = createSeat(1L, "A-10");
		Seat seat2 = createSeat(2L, "A-1");
		Seat seat3 = createSeat(3L, "B-2");
		Seat seat4 = createSeat(4L, "A-2");
		List<Seat> seats = List.of(seat1, seat2, seat3, seat4);

		given(seatRepository.findAllByIdsAndIsDeletedFalse(seatIds)).willReturn(seats);
		given(reservationSeatRepository.findUnavailableSeatIds(seatIds, concertId)).willReturn(List.of());

		// when
		List<Seat> result = seatService.getSeatsForReservation(seatIds, concertId);

		// then - 자연순 정렬 확인 (A-1, A-2, A-10, B-2)
		List<String> expectedOrder = List.of("A-1", "A-2", "A-10", "B-2");
		List<String> actualOrder = result.stream()
			.map(Seat::getSeatNumber)
			.toList();

		// 정렬 결과 출력
		System.out.println("입력 순서: [A-10, A-1, B-2, A-2]");
		System.out.println("기대 결과: " + expectedOrder);
		System.out.println("정렬 결과: " + actualOrder);

		assertThat(actualOrder).isEqualTo(expectedOrder);
	}

	private Seat createSeat(Long id, String seatNumber) {
		Seat seat = mock(Seat.class);
		given(seat.getId()).willReturn(id);
		given(seat.getSeatNumber()).willReturn(seatNumber);
		return seat;
	}
}