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

		//결제 실패 시 롤백
		Reservation reservation = processReservationPayment(request, userId);
		try {

			Payment payment = request.toEntity(reservation);

			Payment savedPayment = paymentRepository.save(payment);

			return PaymentResponse.from(savedPayment, savedPayment.getReservation().getId());
		} catch (DataIntegrityViolationException
				 | ConstraintViolationException
				 | PersistenceException e) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
		} catch (Exception e) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.INSUFFICIENT_BALANCE);
		}
	}

	//결제 내용 정합성 검사
	private Reservation processReservationPayment(PaymentRequest request, Long userId) {

		//todo : Reservation Entity 객체 필요
		//Reservation reservation = reservationService.findReservationById(request.getReservationId())
		Reservation reservation = new Reservation();

		if (!"SUCCESS".equals(request.getStatus())) {
			reservationService.cancelReservation(reservation.getId());
			return reservation;
		}

		int totalAmount = request.getTotalAmount();

		if (totalAmount != reservation.getAmount()) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.INVALID_AMOUNT_REQUEST);
		}

		if (!updatePaymentStatus(totalAmount, userId)) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.NOT_ENOUGH_BALANCE);
		}

		return reservation;
	}

	private boolean updatePaymentStatus(int paidAmount, Long userId) {
		User user = userService.getActiveUserById(userId);

		Long nowBalance = user.getBalance();

		if (nowBalance < paidAmount) {
			return false;
		}

		//결제 처리
		user.updateBalance(nowBalance - paidAmount);
		return true;
	}
}
