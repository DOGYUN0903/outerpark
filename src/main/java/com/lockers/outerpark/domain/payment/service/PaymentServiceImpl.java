package com.lockers.outerpark.domain.payment.service;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.exception.PaymentErrorCode;
import com.lockers.outerpark.domain.payment.exception.PaymentException;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

import jakarta.persistence.PersistenceException;
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
	public PaymentResponse savePaymentHistory(PaymentRequest request, Long reservationId, Long userId) {

		Reservation reservation = processReservationPayment(request, reservationId, userId);
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

	@Override
	@Transactional(readOnly = true)
	public PaymentResponse findOnePaymentHistory(Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(() -> new PaymentException(PaymentErrorCode.NOT_FOUNT_PAYMENT));
		return PaymentResponse.from(payment, payment.getReservation().getId());
	}

	@Override
	@Transactional
	public PaymentResponse cancelPaymentHistory(Long paymentId, Long userId) {
		Payment payment = paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS)
			.orElseThrow(() -> new PaymentException(PaymentErrorCode.NOT_FOUNT_PAYMENT));

		validateCancelable(paymentId);

		payment.updateStatus(PaymentStatus.CANCEL);

		refundPayment(payment.getTotalAmount(), userId);

		return null;
	}

	//결제 내용 정합성 검사
	private Reservation processReservationPayment(PaymentRequest request, Long reservationId, Long userId) {

		//todo : Reservation Entity 객체 필요
		//Reservation reservation = reservationService.findReservationById(reservationId)
		Reservation reservation = new Reservation(); // todo: 임시 구현 삭제 예정

		//결제 실패 시 롤백
		if (request.getStatus() != PaymentStatus.SUCCESS) {
			reservationService.cancelReservation(reservation.getId());
			return reservation;
		}

		int totalAmount = request.getTotalAmount();

		if (totalAmount != reservation.getAmount()) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.INVALID_AMOUNT_REQUEST);
		}

		if (!chargePayment(totalAmount, userId)) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.NOT_ENOUGH_BALANCE);
		}

		return reservation;
	}

	//결제 Balance 처리
	private boolean chargePayment(int paidAmount, Long userId) {
		User user = userService.getActiveUserById(userId);

		Long nowBalance = user.getBalance();

		if (nowBalance < paidAmount) {
			return false;
		}

		//결제 처리
		user.updateBalance(nowBalance - paidAmount);
		return true;
	}

	//결제 취소 Balance 처리
	private void refundPayment(int paidAmount, Long userId) {
		User user = userService.getActiveUserById(userId);

		Long nowBalance = user.getBalance();

		//결제 처리
		user.updateBalance(nowBalance + paidAmount);
	}

	public void validateCancelable(Long paymentId) {
		if (!paymentRepository.isCancelable(paymentId, LocalDateTime.now())) {
			throw new PaymentException(PaymentErrorCode.CANCELLATION_PERIOD_EXPIRED);
		}
	}
}
