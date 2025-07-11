package com.lockers.outerpark.domain.payment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lockers.outerpark.domain.payment.dto.request.PaymentRequest;
import com.lockers.outerpark.domain.payment.dto.response.PaymentSaveResponse;
import com.lockers.outerpark.domain.payment.entity.Payment;
import com.lockers.outerpark.domain.payment.exception.PaymentErrorCode;
import com.lockers.outerpark.domain.payment.exception.PaymentException;
import com.lockers.outerpark.domain.payment.repository.PaymentRepository;
import com.lockers.outerpark.domain.payment.type.PaymentStatus;
import com.lockers.outerpark.domain.reservation.entity.Reservation;
import com.lockers.outerpark.domain.reservation.service.ReservationService;
import com.lockers.outerpark.domain.user.entity.User;
import com.lockers.outerpark.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private ReservationService reservationService;

	@Mock
	private UserService userService;

	@Test
	void 결제가_성공적으로_저장된다() {
		// given
		Long reservationId = 1L;
		Long userId = 42L;
		int amount = 100000;

		PaymentRequest request = new PaymentRequest(amount, "CARD", PaymentStatus.SUCCESS);

		//todo:예약정보 재 구현 필요
		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(amount);

		User user = mock(User.class);
		when(user.getBalance()).thenReturn(200000L);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		Payment payment = Payment.builder()
			.id(10L)
			.reservation(reservation)
			.totalAmount(amount)
			.method("CARD")
			.status(PaymentStatus.SUCCESS)
			.build();

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		// when
		PaymentSaveResponse response = paymentService.savePayment(request, reservationId, userId);

		// then
		assertNotNull(response);
		assertEquals(10L, response.getId());
		verify(paymentRepository).save(any(Payment.class));
	}

	@Test
	void 결제_성공이_아닐_경우_예외_발생() {
		// given
		Long reservationId = 1L;
		Long userId = 33L;
		PaymentRequest request = mock(PaymentRequest.class);
		when(request.getStatus()).thenReturn(PaymentStatus.CANCEL);
		Mockito.doNothing().when(reservationService).cancelReservation(reservationId);

		//when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.savePayment(request, reservationId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.PAYMENT_FAILED, ex.getErrorCode());
	}

	//todo:reservation 객체 연결 시 재 구현
	@Test
	void 결제_금액이_일치하지_않을_경우_예외_발생() {
		// given
		Long reservationId = 1L;
		Long userId = 33L;
		PaymentRequest request = new PaymentRequest(100000, "POINT", PaymentStatus.SUCCESS);
		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(90000);
		Mockito.doNothing().when(reservationService).cancelReservation(reservationId);

		//when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.savePayment(request, reservationId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.INVALID_AMOUNT_REQUEST, ex.getErrorCode());
	}

	//todo:reservation 객체 연결 시 재 구현
	@Test
	void 결제_금액이_부족할_경우_예외_발생() {
		// given
		Long reservationId = 1L;
		Long userId = 33L;
		PaymentRequest request = new PaymentRequest(100000, "POINT", PaymentStatus.SUCCESS);
		Reservation reservation = mock(Reservation.class);
		when(reservation.getAmount()).thenReturn(90000);

		//when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.savePayment(request, reservationId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.INVALID_AMOUNT_REQUEST, ex.getErrorCode());
	}

	@Test
	void 결제_조회시_결제_ID가_일치하지_않으면_예외_발생() {
		//given
		Long paymentId = 1L;

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.findOnePayment(paymentId)
		);

		assertEquals(PaymentErrorCode.NOT_FOUND_PAYMENT, ex.getErrorCode());

	}

	@Test
	void 결제_취소시_결제정보와_유저정보가_정상적으로_업데이트() {
		// given
		Long paymentId = 1L;
		Long userId = 42L;
		int totalAmount = 10000;

		// Payment mock
		Payment payment = Payment.builder()
			.id(paymentId)
			.status(PaymentStatus.SUCCESS)
			.totalAmount(totalAmount)
			.build();

		// User mock
		User user = mock(User.class);
		when(user.getBalance()).thenReturn(30000L);

		// mocking
		when(paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS)).thenReturn(Optional.of(payment));
		when(paymentRepository.isCancelable(paymentId)).thenReturn(true);
		when(userService.getActiveUserById(userId)).thenReturn(user);

		// when
		paymentService.cancelPayment(paymentId, userId);

		// then
		assertEquals(PaymentStatus.CANCEL, payment.getStatus());
		verify(user).updateBalance(40000L);
	}

	@Test
	void 결제_조회시_결제_정보가_없으면_예외_발생() {
		//given
		Long paymentId = 1L;
		Long userId = 3L;

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.cancelPayment(paymentId, userId)
		);

		assertEquals(PaymentErrorCode.NOT_FOUND_PAYMENT, ex.getErrorCode());

	}

	@Test
	void 공연날짜가_지난_경우_결제취소시_예외_발생() {
		// given
		Long paymentId = 1L;
		Long userId = 33L;

		Payment mockPayment = Payment.builder()
			.id(paymentId)
			.totalAmount(10000)
			.status(PaymentStatus.SUCCESS)
			.reservation(new Reservation())
			.build();

		// 결제 상태 SUCCESS인 Payment 찾기 → 성공
		Mockito.when(paymentRepository.findByIdAndStatus(paymentId, PaymentStatus.SUCCESS))
			.thenReturn(Optional.of(mockPayment));

		// 공연 날짜가 이미 지남
		Mockito.when(paymentRepository.isCancelable(paymentId))
			.thenReturn(false);

		// when
		PaymentException ex = assertThrows(PaymentException.class, () ->
			paymentService.cancelPayment(paymentId, userId)
		);

		//then
		assertEquals(PaymentErrorCode.CANCELLATION_PERIOD_EXPIRED, ex.getErrorCode());
	}
}

