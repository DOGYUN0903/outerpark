package com.lockers.outerpark.domain.reservation.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lockers.outerpark.domain.reservation.dto.request.ReservationRequest;
import com.lockers.outerpark.domain.reservation.dto.response.ReservationResponse;
import com.lockers.outerpark.domain.reservation.repository.ReservationRepository;
import com.lockers.outerpark.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final UserService userService;
	private final ReservationRepository reservationRepository;

	@Override
	public ReservationResponse createReservation(ReservationRequest request, Long userId) {
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
}
