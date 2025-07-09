package com.lockers.outerpark.domain.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.concert.entity.Concert;
import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.repository.ReservationRepository;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final UserService userService;
	private final ReservationRepository reservationRepository;

	@Override
	@Transactional
	public ReservationResponse createReservation(ReservationRequest request, Long userId, Long concertId) {
		User user = userService.getActiveUserById(userId);
		List<Long> seatIds = request.getSeatIds();
		List<Integer> seatNumbers = new ArrayList<>();

		// for (Long seatId : seatIds) {
		// 	seatNumbers.add(seat.getSeatNumber());
		// 	seatService.reserveSeat(seatId);
		// 	String reservationNumber = CreateReservationNumber(concert, seat.getSeatNumber());
		// 	Reservation reservation = new Reservation(user, seat, reservationNumber,
		// 		request.getAmount() / seatIds.size());
		// }

		// ReservationResponse.fromEntity(savedReservation, concert);

		return null;
	}

	@Override
	public void cancelReservation(Long reservationId) {

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
		return reservationRepository.findById(reservationId).orElseThrow();
	}

	/**
	 * private 메서드
	 */
	private String createReservationNumber(Concert concert, int seatNumber) {
		return "T" + String.format("%04d", concert.getId()) + String.format("%02d",
			concert.getPerformanceDate().getDayOfMonth()) + String.format("%03d", seatNumber);
	}
}
