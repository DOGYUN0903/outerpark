package com.lockers.outerpark.domain.payment.service;

import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentResponse;
import com.lockers.outerpark.domain.payment.dto.response.PaymentSaveResponse;
import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.exception.PaymentErrorCode;
import com.lockers.outerpark.domain.payment.exception.PaymentException;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.reservation.type.ReservationStatus;
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
	public PaymentSaveResponse createPayment(PaymentRequest request, Long concertId, Long userId) {

		//결제 정합성 검사(결제 금액 및 예약 번호 확인)
		Reservation reservation = processReservationPayment(request, concertId, userId);

		try {
			//결제 정보 Balance 반영
			chargePayment(reservation, userId);

			//RequestDto 를 Entity 로 변환
			Payment payment = request.toEntity(reservation);

			//결제 정보 저장
			Payment savedPayment = paymentRepository.save(payment);

			//예약 정보 Confirm 변경
			reservation.updateStatus(ReservationStatus.CONFIRMED);

			//결제 정보 ID 반환
			return new PaymentSaveResponse(savedPayment.getId());

		} catch (DataIntegrityViolationException
				 | ConstraintViolationException
				 | PersistenceException e) {
			//save 과정 중 문제 있을 시 롤백 및 예외 처리
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
		} catch (Exception e) {
			//원인을 알 수 없는 문제 발생 시 롤백 및 예외처리
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.PAYMENT_FAILED);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaymentResponse getPayment(Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(() -> new PaymentException(PaymentErrorCode.NOT_FOUND_PAYMENT));
		return PaymentResponse.from(payment, payment.getReservation().getId());
	}

	@Override
	@Transactional
	public void cancelPayment(Long paymentId, Long userId) {
		//PaymentStatus 가 SUCCESS 인 경우 가져옴
		Payment payment = paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS)
			.orElseThrow(() -> new PaymentException(PaymentErrorCode.NOT_FOUND_PAYMENT));

		//공연 날짜가 당일 포함 지났을 경우 환불 불가 (예외처리)
		validateCancelable(paymentId);

		//결제 정보 CANCEL 업데이트
		payment.updateStatus(PaymentStatus.CANCEL);

		//유저 Balance 업데이트
		refundPayment(payment.getTotalAmount(), userId);
	}

	//결제 정합성 검사
	private Reservation processReservationPayment(PaymentRequest request, Long concertId, Long userId) {

		Reservation reservation = reservationService.findReservationByUserIdAndConsortId(userId, concertId);

		//결제 실패 시 예약 롤백 및 예외
		if (request.getStatus() != PaymentStatus.SUCCESS) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.PAYMENT_FAILED);
		}

		return reservation;
	}

	//결제 Balance 처리
	private void chargePayment(Reservation reservation, Long userId) {
		User user = userService.getActiveUserById(userId);

		Long nowBalance = user.getBalance();

		int paidAmount = reservation.getAmount();

		//자금이 결제 금액보다 적을 경우 예약 롤백 및 예외
		if (nowBalance < paidAmount) {
			reservationService.cancelReservation(reservation.getId());
			throw new PaymentException(PaymentErrorCode.NOT_ENOUGH_BALANCE);
		}

		//결제 처리
		user.updateBalance(nowBalance - paidAmount);
	}

	//결제 취소 Balance 처리
	private void refundPayment(int paidAmount, Long userId) {
		User user = userService.getActiveUserById(userId);

		//결제 처리
		user.updateBalance(user.getBalance() + paidAmount);
	}

	//공연 날짜가 당일 포함 지났을 경우 예외처리
	public void validateCancelable(Long paymentId) {

		if (paymentRepository.countCancelable(paymentId, LocalDate.now().plusDays(1)) == 0) {
			throw new PaymentException(PaymentErrorCode.CANCELLATION_PERIOD_EXPIRED);
		}
	}
}
