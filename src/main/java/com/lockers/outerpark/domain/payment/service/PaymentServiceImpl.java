package com.lockers.outerpark.domain.payment.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lockers.outerpark.domain.payment.dto.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.PaymentResponse;
import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.exception.PaymentErrorCode;
import com.lockers.outerpark.domain.payment.exception.PaymentException;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final ReservationService reservationService;

	private final UserService userService;

	@Override
	@Transactional
	public PaymentResponse savePaymentHistory(PaymentRequest request, Long userId) {
		Reservation reservation = processReservationPayment(request, userId);
		try {

			Payment payment = request.toEntity(reservation);

			Payment savedPayment = paymentRepository.save(payment);

			return PaymentResponse.from(savedPayment, savedPayment.getReservation().getId());
		} catch (DataIntegrityViolationException
				 | ConstraintViolationException
				 | PersistenceException e) {
			//todo: 예약 내역 취소 로직
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
		} catch (Exception e) {
			//todo: 예약 내역 취소 로직
			throw new PaymentException(PaymentErrorCode.INSUFFICIENT_BALANCE);
		}
	}

	private Reservation processReservationPayment(PaymentRequest request, Long userId) {

		//todo : Reservation Entity 객체 필요
		//Reservation reservation = reservationService.findReservationById(request.getReservationId())

		int totalAmount = request.getTotalAmount();

		Reservation reservation = new Reservation();

		if (totalAmount != reservation.getAmount()) {
			//todo: 예약 내역 취소 로직
			throw new PaymentException(PaymentErrorCode.INVALID_AMOUNT_REQUEST);
		}

		if (!updatePaymentStatus(totalAmount, userId)) {
			//todo: 예약 내역 취소 로직
			throw new PaymentException(PaymentErrorCode.NOT_ENOUGH_BALANCE);
		}

		return reservation;
	}

	private boolean updatePaymentStatus(int totalAmount, Long userId) {
		//todo : User Entity 객체 필요
		//User user = userService.getUserById(userId);

		User user = new User();

		Long balance = user.getBalance();

		if (balance < totalAmount) {
			return false;
		}

		//todo: user 보유 balance 변경(setBalance 필요)

		return true;
	}
}
