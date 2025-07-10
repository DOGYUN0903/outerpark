package com.lockers.outerpark.domain.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.concert.service.ConcertService;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.entity.ReservationStatus;
import com.lockers.outerpark.domain.reservation.repository.ReservationRepository;
import com.lockers.outerpark.domain.seat.entity.ReservationSeat;
import com.lockers.outerpark.domain.seat.entity.Seat;
import com.lockers.outerpark.domain.seat.service.SeatService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final UserService userService;
	private final ConcertService concertService;
	private final SeatService seatService;
	private final ReservationRepository reservationRepository;

	@Override
	@Transactional
	public ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId) {
		User user = userService.getActiveUserById(userId);
		// TODO: seat, concert 수정
		Concert concert = new Concert();
		List<Long> seatIds = request.getSeatIds();
		List<Seat> seats = new ArrayList<>();

		for (Long seatId : seatIds) {
			seats.add(new Seat());
		}

		Reservation reservation = new Reservation(user, concert, seatIds.size(), concert.getPrice() * seatIds.size());

		// TODO: Seat 엔티티 객체 가져오는 메서드 필요
		for (Seat seat : seats) {
			String reservationNumber = createReservationNumber(concert, seat.getSeatNumber());
			ReservationSeat reservationSeat = new ReservationSeat(reservation, seat, reservationNumber);

			reservation.addReservationSeat(reservationSeat);
		}

		Reservation savedReservation = reservationRepository.save(reservation);

		return ReservationResponse.fromEntity(savedReservation, concert, seats);
	}

	@Override
	@Transactional
	public void cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findByIdAndStatusNot(reservationId, ReservationStatus.CANCELLED)
			.orElseThrow(() -> new RuntimeException());

		reservation.cancel();
	}

	@Transactional
	@Override
	public void confirmReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findByIdAndStatus(reservationId, ReservationStatus.PENDING)
			.orElseThrow(() -> new RuntimeException());

		reservation.confirm();
	}

	@Override
	public ReservationResponse getUserReservation(Pageable pageable, Long userId) {
		return null;
	}

	@Override
	public boolean existsReservation(Long reservationId) {
		if (reservationId == null)
			return false;
		return reservationRepository.existsById(reservationId);
	}

	@Override
	public Reservation findReservationById(Long reservationId) {
		return reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException());
	}

	/**
	 * private 메서드
	 */
	private String createReservationNumber(Concert concert, int seatNumber) {
		return "T" + String.format("%04d", concert.getId()) + String.format("%02d",
			concert.getPerformanceDate().getDayOfMonth()) + String.format("%03d", seatNumber);
	}
}
